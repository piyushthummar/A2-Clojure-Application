; References
; https://xvrdm.github.io/2017/06/08/clojure-for-pythonista-user-input-loop-conditional/#:~:text=In%20clojure%2C%20you%20can%20get,to%20the%20console%20with%20println%20.


(def cust_ID_Name_Map {})
(def print_Customer_Info_Map {})

(def prod_ID_Name_Map {})
(def prod_ID_Cost_Map {})
(def print_Prod_Info_Map {})

(def sales_ID_Cust_ID_Map {})
(def sales_ID_Prod_ID_Map {})
(def sales_ID_ItemCount_Map {})
(def print_Sales_Info_Map {})

(def totalSales_By_Product_Map {})
(def totalCost 0.0)

(def totalItemCount 0)


; Loads customer data before printing menu
(defn loadCustomerData [fileName]
  (def lines (clojure.string/split-lines (slurp fileName)))
  ; https://stackoverflow.com/questions/25948813/read-line-by-line-for-big-files
  ; https://lethain.com/reading-file-in-clojure/
  (doseq [line lines]
    ;(println line)
    (def data_by_record (clojure.string/split line #"\|"))
    (def cust_ID_Name_Map (assoc cust_ID_Name_Map (get data_by_record 0) (get data_by_record 1)))
    ;(println cust_ID_Name_Map)
    (def restData (str ":[" (get data_by_record 1) ", " (get data_by_record 2) ", " (get data_by_record 3) "]"))
    ;(println restData)
    (def print_Customer_Info_Map (assoc print_Customer_Info_Map (get data_by_record 0) restData))
    )
  ;(println print_Customer_Info_Map)
  )

; when choice is 1, then display customers data
(defn displayCustomerTable []
  ;(println "in 1st option")
  ; https://clojuredocs.org/clojure.core/sorted-map
  (def print_Customer_Info_Map (into (sorted-map) print_Customer_Info_Map))
  (println "-------------------------------------------------")
  (println "\t\tCUSTOMER TABLE")
  (println "-------------------------------------------------")
  ; https://stackoverflow.com/questions/6685916/how-to-iterate-over-map-keys-and-values-in-clojure
   (doseq [[k v] print_Customer_Info_Map]
     (println (str k (get print_Customer_Info_Map k))))
  (println "-------------------------------------------------")
  ;(println print_Customer_Info_Map)
  ;(println "after doseq")
  )

; Loads product data before printing menu
(defn loadProductData [fileName]
  (def lines (clojure.string/split-lines (slurp fileName)))

  (doseq [line lines]
    ;(println line)
    (def data_by_record (clojure.string/split line #"\|"))
    (def prod_ID_Name_Map (assoc prod_ID_Name_Map (get data_by_record 0) (get data_by_record 1)))
    (def prod_ID_Cost_Map (assoc prod_ID_Cost_Map (get data_by_record 0) (get data_by_record 2)))
    ;(println cust_ID_Name_Map)
    (def restData (str ":[" (get data_by_record 1) ", " (get data_by_record 2) "]"))
    ;(println restData)
    (def print_Prod_Info_Map (assoc print_Prod_Info_Map (get data_by_record 0) restData))
    )
  ;(println print_Customer_Info_Map)
  )

;  when choice is 2, then display product data
(defn displayProductTable
  "It will display product table in a format"
  []
  (def print_Prod_Info_Map (into (sorted-map) print_Prod_Info_Map))
  (println "-------------------------------------------------")
  (println "\t\tPRODUCT TABLE")
  (println "-------------------------------------------------")
  ; https://stackoverflow.com/questions/6685916/how-to-iterate-over-map-keys-and-values-in-clojure
  (doseq [[k v] print_Prod_Info_Map]
    (println (str k (get print_Prod_Info_Map k))))
  (println "-------------------------------------------------")
  )

; Loads sales data before printing menu
(defn loadSalesData [fileName]
  (def lines (clojure.string/split-lines (slurp fileName)))
  ; https://stackoverflow.com/questions/25948813/read-line-by-line-for-big-files
  ; https://lethain.com/reading-file-in-clojure/
  (doseq [line lines]
    ;(println line)
    (def data_by_record (clojure.string/split line #"\|"))
    (def sales_ID_Cust_ID_Map (assoc sales_ID_Cust_ID_Map (get data_by_record 0) (get data_by_record 1)))
    (def sales_ID_Prod_ID_Map (assoc sales_ID_Prod_ID_Map (get data_by_record 0) (get data_by_record 2)))
    (def sales_ID_ItemCount_Map (assoc sales_ID_ItemCount_Map (get data_by_record 0) (get data_by_record 3)))
    ;(println cust_ID_Name_Map)
    (def restData (str ":[" (get cust_ID_Name_Map (get data_by_record 1)) ", " (get prod_ID_Name_Map (get data_by_record 2)) ", " (get data_by_record 3) "]"))
    ;(println restData)
    (def print_Sales_Info_Map (assoc print_Sales_Info_Map (get data_by_record 0) restData))
    )
  ;(println print_Customer_Info_Map)
  )

;  when choice is 3, then display product data
(defn displaySalesTable
  "It will display sales table in a format"
  []
  (def print_Sales_Info_Map (into (sorted-map) print_Sales_Info_Map))
  (println "-------------------------------------------------")
  (println "\t\tSALES TABLE")
  (println "-------------------------------------------------")
  ; https://stackoverflow.com/questions/6685916/how-to-iterate-over-map-keys-and-values-in-clojure
  (doseq [[k v] print_Sales_Info_Map]
    (println (str k (get print_Sales_Info_Map k))))
  (println "-------------------------------------------------")
  )

; when choice is 4, then calculate and display total purchase of given customer
(defn totalSalesForCustomer
  "This will count total sales of customer"
  []
  ; counting total cost for each sale ID
  (doseq [[k v] sales_ID_Cust_ID_Map]
    (def productId (get sales_ID_Prod_ID_Map k))
    (def productCost (get prod_ID_Cost_Map productId))
    (def itemCount (get sales_ID_ItemCount_Map k))
    ;(println productCost)
    ;(println itemCount)
    (def totalSales_By_Product_Map (assoc totalSales_By_Product_Map k (* (Float/parseFloat productCost)  (Integer/parseInt itemCount) ))))

  (def totalCost 0.0)

  (println "Enter customer name to count total sale : ")
  (def customerName (read-line))
    ; https://stackoverflow.com/questions/18176372/clojure-get-map-key-by-value
    (def custIDForName (keep #(when (= (val %) customerName) (key %)) cust_ID_Name_Map))

    (println "-----------------------------------------------------")
    (if (empty? custIDForName) (do (def totalCost 0.0) (println "Given customer is not present in the customer table.") ) (doseq [[k v] sales_ID_Cust_ID_Map]
                                                     ;(println (type v))
                                                     (if (= (Integer/parseInt v) (Integer/parseInt (apply str custIDForName)))
                                                       (def totalCost (+ totalCost (get totalSales_By_Product_Map k) ) )                          ;(Float/parseFloat totalCost) (Float/parseFloat (get totalSales_By_Product_Map k))
                                                       )
                                                     ))
    ;(println custIDForName)

    ;(println (type custIDForName))
    ;(println (type (str custIDForName)))

    ;(doseq [[k v] sales_ID_Cust_ID_Map]
    ;  ;(println (type v))
    ;  (if (= (Integer/parseInt v) (Integer/parseInt (apply str custIDForName)))
    ;    (def totalCost (+ totalCost (get totalSales_By_Product_Map k) ) )                          ;(Float/parseFloat totalCost) (Float/parseFloat (get totalSales_By_Product_Map k))
    ;    )
    ;  )
    ; https://clojuredocs.org/clojure.core/with-precision
    ;(println "-------------------------------------------------")
    (println (str customerName ": $" (format "%.2f" totalCost)))                     ;(with-precision 2 (Float/parseFloat totalCost))
    (println "-----------------------------------------------------")
    )

; when choice is 5, then calculate and display total count of given product
(defn totalCountForProduct
  "This will return the total count of the product for given customer"
  []


  (println "Enter product name to count total items : ")
  (def productName (read-line))
  ;(println productName)
  ; https://stackoverflow.com/questions/18176372/clojure-get-map-key-by-value
  (def prodIdForSales (keep #(when (= (val %) productName) (key %)) prod_ID_Name_Map))
  (def totalItemCount 0)

  (println "-------------------------------------------------")
  (if (empty? prodIdForSales) (do (def totalItemCount 0) (println "Given item is not present in the product table.")) (doseq [[k v] sales_ID_Prod_ID_Map]
                                                            (if (= (Integer/parseInt v) (Integer/parseInt (apply str prodIdForSales)))
                                                              (def totalItemCount (+  totalItemCount (Long/parseLong (get sales_ID_ItemCount_Map k))) )
                                                              )
                                                            ))
  ;(println prodIdForSales)
  ;(println (apply str prodIdForSales))

  ;(doseq [[k v] sales_ID_Prod_ID_Map]
  ;  ;(println v)
  ;  ;(println (apply str prodIdForSales))
  ;  (if (= (Integer/parseInt v) (Integer/parseInt (apply str prodIdForSales)))
  ;    (def totalItemCount (+  totalItemCount (Long/parseLong (get sales_ID_ItemCount_Map k))) )
  ;    ;(do (println (type (Long/parseLong (get sales_ID_ItemCount_Map k)) ))
  ;    ;    (println (type totalItemCount))
  ;    ;  (def totalItemCount (+  totalItemCount (Long/parseLong (get sales_ID_ItemCount_Map k))) )                         ;(Integer/parseInt totalItemCount) (Integer/parseInt (get sales_ID_ItemCount_Map k))
  ;    ;    (println "inside if after count"))
  ;    )
  ;  )

  ;(println "-------------------------------------------------")
  (println (str productName ": " totalItemCount))
  (println "-------------------------------------------------")

  )

; Calls all 3 load functions
(defn loadData []
  (loadCustomerData "cust.txt")
  (loadProductData "prod.txt")
  (loadSalesData "sales.txt")
  )

; Main displayMenu function, that starts this application
(defn displayMenu []
  (loadData)
  (println "*** Sales Menu ***\n------------------\n1. Display Customer Table\n2. Display Product Table\n3. Display Sales Table\n4. Total Sales for Customer\n5. Total Count for Product\n6. Exit\nEnter an option?")
  (let [choice (read-line)]              ;(Integer/parseInt (read-line))
    ;(println "Your choice is " choice)
    ; https://www.tutorialspoint.com/clojure/clojure_cond_statement.htm
      (cond
        (= choice "1") (displayCustomerTable)
        (= choice "2") (displayProductTable)
        (= choice "3") (displaySalesTable)
        (= choice "4") (totalSalesForCustomer)
        (= choice "5") (totalCountForProduct)
        (= choice "6") (do (println "Good Bye!") (System/exit 0))
        :else (println "Please enter correct choice between 1 to 6")
        )
    (recur)
    ;(if (= choice "6") (System/exit 0) (recur))
    )
    ;(if (< choice 1 or > choice 6) (println "No valid choice") (recur)))

  )

(displayMenu)