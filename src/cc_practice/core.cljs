(ns cc-practice.core
  (:require [clojure.browser.repl :as repl]
            [matchbox.core :as m]))

(defonce conn
    (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(println "Hello world!")

(def root (m/connect "https://el-sistema.firebaseio.com"))

(m/auth-anon root)

(m/listen-children
  root [:users :mike :friends]
  (fn [[event-type data]] (prn event-type data)))

(def mikes-friends (m/get-in root [:users :mike :friends]))
(m/reset! mikes-friends [{:name "Kid A"} {:name "Kid B"}])
(m/conj! mikes-friends {:name "Jean"})

(m/deref
  mikes-friends
  (fn [key value]
    (m/reset-in! root [:users :mike :num-friends]
                 (count value))))