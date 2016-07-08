(ns ivoice-api.selmer
  (:require [selmer.parser :refer :all]))

(set-resource-path! "")

(defn rand-cond [] (let [x (rand-int 2)] (if (= 1 x) true false)))

(defn selmer-demo-handler [req]
  (render-file "home.html" 
               {:name "Yogthos"
                :condition false
                :items [{:name "abc" :condition (rand-cond) :text "111111"}
                        {:name "def" :condition (rand-cond)  :text "222222"}
                        {:name "ghi" :condition (rand-cond)  :text "33333"}
                        {:name "jkl" :condition (rand-cond)  :text "444444"}
                        {:name "mno" :condition (rand-cond)  :text "555555"}
                        {:name "pqr" :condition (rand-cond)  :text "666666"}
                        ]}))