(defproject svn-markov "0.1.0-SNAPSHOT"
  :description "generate fake commit messages from svn history"
  :url "http://github.com/lnmx/svn-markov-clj"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot svn-markov.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
