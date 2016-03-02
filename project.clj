(defproject svn-markov "0.1.0-SNAPSHOT"
  :description "generate fake commit messages from svn history"
  :url "http://github.com/lnmx/svn-markov-clj"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot svn-markov.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
