(ns ivoice-api.extract
  (:require [ivoice-api.util :refer :all]))

(defn upload-extract 
  [{{{:keys [content-type size filename tempfile]} :resource
    type :type} :params}]
  (let [m {:content-type content-type
  		   :size size
  		   :filename filename
  		   :tempfile tempfile
  		   :type type}
  		satisfy (every? identity (vals m))
      type-value (parse-int type)]
    (if (and satisfy
    		     type-value) 
      (assoc m :type-value type-value) ) ) )
