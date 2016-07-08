(ns ivoice-api.upload
  (:require [clojure.java.io :as io]
            [ivoice-api.cfg :refer :all])
  (:import [java.time LocalDate format.DateTimeFormatter]))

(def resource-type-set #{"video/mp4"})
(def image-type-set #{"image/jpeg"})

(defn date-path []
  (-> (LocalDate/now)
  	  (.format DateTimeFormatter/ISO_LOCAL_DATE)))

(defn storage-resource [tempfile dir]
  (let [target (.toString (java.util.UUID/randomUUID))
        location (date-path)
  		  dir-path (str STORAGE-PATH dir location)
  	    target-file (fn [path] 
      			  	      (let [dir (io/file path)] 
      		   	    		  (when-not (.exists dir) (.mkdirs dir))
      			  	    	  (io/file (str path  "/" target))))]
    (io/copy tempfile (target-file dir-path))
    {:target target
     :location location}))

(defn upload 
  [{:keys [tempfile type-value]}]
  (let [dir (cond (RESOURCE-SET type-value) RESOURCE
                  (IMAGE-SET type-value) IMAGE 
                  :else MULTIPLE)]
  (storage-resource tempfile dir)))
