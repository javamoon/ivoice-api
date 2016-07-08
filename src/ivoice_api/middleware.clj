(ns ivoice-api.middleware
  (:require [ring.util.response :refer :all]))

(defn wrap-content-type-json 
  [handler]
  (fn [request]
    (let [response (handler request)]
      (prn response)
      (-> response 
          (content-type "application/json")
          (header "Access-Control-Allow-Origin" "*")
          (header "Access-Control-Allow-Methods" "POST,GET")
          (header "Access-Control-Allow-Credentials" "true")))))

(defn wrap-echo-request 
  [handler]
  (fn [req] 
    (println) 
    (println (str (.toString (java.time.LocalDateTime/now)) " :"))
	(prn req) 
	(handler req)))

(defn wrap-echo-json [handler]
  (-> handler
      wrap-echo-request
      wrap-content-type-json))
