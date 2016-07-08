(ns ivoice-api.carmine
  (:require [taoensso.carmine :as car :refer [wcar]]))

(def conn {:pool {}
	  	   :spec {:host ""
		 		  :port 6379 
		 		  :password "" 
		 		  :timeout 4000}})

(defmacro wcar* [& body] `(car/wcar conn ~@body))

