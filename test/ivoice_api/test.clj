{:ssl-client-cert nil, 
 :protocol "HTTP/1.1", 
 :remote-addr "0:0:0:0:0:0:0:1", 

 :params {"resource" {:filename "20151006-3.jpg", 
 					  :content-type "image/jpeg", 
 					  :tempfile #object[java.io.File 0x25452516 "/var/folders/yn/wnhwlrp50db83yq8zl8h1s040000gn/T/ring-multipart-3726969794462412612.tmp"], 
 					  :size 565397}, 
 		  "text2" "test123", 
 		  "submit" "commit"}, 

 :route-params {}, 
 :headers {"origin" "null", 
 		   "host" "localhost:3001", 
 		   "user-agent" "Mozilla/5.0 (Macintosh\; Intel Mac OS X 10_10_5) AppleWebKit/537.36 
 		   (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36", 
 		   "content-type" "multipart/form-data; boundary=----WebKitFormBoundaryzAgOOixD3FU0grGv", 
 		   "cookie" "__utma=111872281.277930019.1459605766.1459670374.1459692072.4; 
 		   __utmz=111872281.1459605766.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none);
 		    JSESSIONID=0EDC164016021FEAACDDD81E0686E97F", "content-length" "565787", 
 		    "connection" "keep-alive", "upgrade-insecure-requests" "1", "accept" 
 		    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8", 
 		    "accept-language" "zh-CN,zh;q=0.8,en;q=0.6", "accept-encoding" "gzip, deflate",
 		     "cache-control" "max-age=0"}, 
 :server-port 3001, 
 :content-length 565787, 
 :compojure/route [:post "/upload"],
 :content-type "multipart/form-data； boundary=----WebKitFormBoundaryzAgOOixD3FU0grGv", 
 :path-info "/upload", 
 :character-encoding nil, 
 :context "/ivoice/api/resource", 
 :uri "/ivoice/api/resource/upload", 
 :server-name "localhost", 
 :query-string nil, 
 :body #object[org.eclipse.jetty.server.HttpInputOverHTTP 0x2b82cfce "HttpInputOverHTTP@2b82cfce"], 

 :multipart-params {"resource" {:filename "20151006-3.jpg", 
 							    :content-type "image/jpeg", 
 							    :tempfile #object[java.io.File 0x25452516 "/var/folders/yn/wnhwlrp50db83yq8zl8h1s040000gn/T/ring-multipart-3726969794462412612.tmp"], 
 							    :size 565397}, 
 					"text2" "test123", 
 					"submit" "commit"}, 

 :scheme :http, 
 :request-method :post}