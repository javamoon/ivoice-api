(ns ivoice-api.msgsend
  (:import [service RespSubmitSMS SMSWebService SMSWebServiceServiceLocator]))

(def ACCOUNT "")

(def PASSWORD "")

(defn send-code-msg [phone]
  (let [msg (format "您的登陆验证码为：%04d，10分钟内有效。【】" (rand-int 10000))
		    dStr (-> (java.time.LocalDateTime/now) 
  		    	     (.format (java.time.format.DateTimeFormatter/ofPattern "yyyyMMddHHmmss")))
  	  	rn (+ 10000 (rand-int 1000000))
	      taskId (str "" dStr "_service_" rn)]
  	(-> (SMSWebServiceServiceLocator.)
  		  .getSMSWebServicePort
  	    (.sendBatchMessage ACCOUNT PASSWORD msg nil phone taskId)
  	    .getResult)))
