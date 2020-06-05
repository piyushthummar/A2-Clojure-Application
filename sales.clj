; References
; https://xvrdm.github.io/2017/06/08/clojure-for-pythonista-user-input-loop-conditional/#:~:text=In%20clojure%2C%20you%20can%20get,to%20the%20console%20with%20println%20.


(def cust_ID_Name_Map {})
(def print_Customer_Info_Map {})

(defn loadCustomerData [fileName]
  (def lines (clojure.string/split-lines (slurp fileName)))
  ; https://stackoverflow.com/questions/25948813/read-line-by-line-for-big-files
  ; https://lethain.com/reading-file-in-clojure/
  (doseq [line lines]
    ;(println line)
    (def data_by_record (clojure.string/split line #"\|"))
    (def cust_ID_Name_Map (assoc cust_ID_Name_Map (get data_by_record 0) (get data_by_record 1)))
    ;(println cust_ID_Name_Map)
    (def restData (str ":[" (get data_by_record 1) " , " (get data_by_record 2) " , " (get data_by_record 3) "]\n"))
    ;(println restData)
    (def print_Customer_Info_Map (assoc print_Customer_Info_Map (get data_by_record 0) restData))
    )
  ;(println print_Customer_Info_Map)
  )

; when choice is 1, then display customers data
(defn displayCustomerTable []
  ;(println "in 1st option")
  ; https://stackoverflow.com/questions/6685916/how-to-iterate-over-map-keys-and-values-in-clojure
   (doseq [[k v] print_Customer_Info_Map]
     (println (str k (get print_Customer_Info_Map k))))
  ;(println print_Customer_Info_Map)
  ;(println "after doseq")
  )

;  when choice is 2, then display product data
(defn displayProductTable
  "It will display product table in a format"
  []
  (println "inside display product function")
  )

;  when choice is 2, then display product data
(defn displayProductTable
  "It will display product table in a format"
  []
  (println "inside display product function")
  )


; Main displayMenu function, that starts this application
(defn displayMenu []
  (println (loadCustomerData "cust.txt"))
  ;(loadData "prod.txt")
  ;(loadData "sales.txt")
  (println "*** Sales Menu ***\n------------------\n1. Display Customer Table\n2. Display Product Table\n3. Display Sales Table\n4. Total Sales for Customer\n5. Total Count for Product\n6. Exit\nEnter an option?")
  (let [choice (read-line)]              ;(Integer/parseInt (read-line))
    ;(println "Your choice is " choice)
    ; https://www.tutorialspoint.com/clojure/clojure_cond_statement.htm
      (cond
        (= choice "1") (displayCustomerTable)
        (= choice "2") (displayProductTable)
        (= choice "3") (println "Display sales")
        (= choice "4") (println "Display Total items")
        (= choice "5") (println "Display Total sale")
        (= choice "6") (do (println "Good Bye!") (System/exit 0))
        :else (println "Please enter correct choice between 1 to 6")
        )
    (recur)
    ;(if (= choice "6") (System/exit 0) (recur))
    )
    ;(if (< choice 1 or > choice 6) (println "No valid choice") (recur)))

  )

(displayMenu)