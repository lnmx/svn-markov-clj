(ns svn-markov.core
  (require [svn-markov.svn :as svn]
           [svn-markov.chain :as chain]
           [clojure.pprint :refer [pprint]]
           [clojure.string :as str])
  (:gen-class))


(defn- read-svn-log [path]
  (let [commits (svn/parse path)]
    (println "loaded" path "with" (count commits) "commits")
    commits))

(defn- parse-comment [comment]
  (->
    (str/trim comment)
    (str/split #"\s+")))

(defn- render-comment [words]
  (str/join " " words))

(defn- build [n]
  "build a markov chain of depth 'n' from svn logs"
  (let [svn-log   (read-svn-log "history.xml")
        comments (map :comment svn-log)
        words    (map parse-comment comments)
        words    (filter #(>= (count %1) 3) words)
        model    (reduce chain/add (chain/start n) words)]
    model))


(defn -main
  "build a markov chain from svn logs, then generate new text from the resulting model"
  [& args]
  (let [n     2
        max   50
        model (build n)]
    (println "ready\n")
    (read-line)
    (loop []
      (let [comment (chain/generate model max)]
        (println "=>" (render-comment comment))
        (if-not (nil? (read-line)) (recur))))))

