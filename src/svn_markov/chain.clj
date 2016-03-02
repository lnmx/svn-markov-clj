(ns svn-markov.chain
  (require [clojure.pprint :refer [pprint]]))


;; build =======================================================

(defn- mark [path]
  "add ::start and ::end markers to path"
  (vec (concat [::start] path [::end])))


(defn- path->ngrams [n path]
  "divide path into ngrams of length n"
  (partition n 1 path))


(defn- ngram->table [ngram]
  "convert an ngram into a table entry"
  {(butlast ngram) [(last ngram)]})


(defn- merge-tables [tables]
  "merge a sequence of tables, returning the merged result"
  (apply merge-with (comp vec concat) tables))


(defn- path->table [n path]
  "convert a path (sequence of states) into a table"
  (let [path   (mark path)
        ngrams (path->ngrams (inc n) path)
        tables (map ngram->table ngrams)]
    (merge-tables tables)))

;; generate ====================================================

(defn- seeds [model]
  "return the seeds (ngrams starting with ::start) in model"
  (filter #(= ::start (first %1))
          (keys (:table model))))


(defn- random-seed [model]
  "return a random seed from model"
  (rand-nth (seeds model)))


(defn- step [model input]
  "choose a new state from model from input"
  (let [choices ((:table model) input [::end])]
    (rand-nth choices)))


(defn- execute [model max]
  "execute the model, generating a new path up to max length"
  (let [n (:n model)]
    (loop [output (random-seed model)]
      (let [input  (take-last n output)
            next   (step model input)
            output (concat output [next])]
        (if (and (not= ::end next) (< (count output) max))
          (recur output)
          output)))))


(defn- unmark [path]
  "remove ::start and ::end markers from path"
  (let [is-marker #{::start ::end}]
    (vec (filter (complement is-marker) path))))


;; api =========================================================

(defn start [n]
  "create and return a chain model of depth 'n'"
  {:n     n
   :table {}})


(defn add [model path]
  "add 'path' to 'model', returning the new model; path is a seq of state values"
  (let [n         (:n model)
        add-table (path->table n path)
        new-table (merge-tables [(:table model) add-table])]
    (assoc model :table new-table)))


(defn generate [model max]
  "generate a path of up to 'max' states from 'model'"
  (unmark (execute model max)))
