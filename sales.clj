; References
; https://xvrdm.github.io/2017/06/08/clojure-for-pythonista-user-input-loop-conditional/#:~:text=In%20clojure%2C%20you%20can%20get,to%20the%20console%20with%20println%20.

; For option 1 - Customer Table
(def cust_ID_Name_Map {})
(def print_Customer_Info_Map {})

; For option 2 - Product Table
(def prod_ID_Name_Map {})
(def prod_ID_Cost_Map {})
(def print_Prod_Info_Map {})

; For option 3 - Sales Table
(def sales_ID_Cust_ID_Map {})
(def sales_ID_Prod_ID_Map {})
(def sales_ID_ItemCount_Map {})
(def print_Sales_Info_Map {})

; For option 4 - Total sales of customer
(def totalSales_By_Product_Map {})
(def totalCost 0.0)

; For option 5 - Total items of product that is sold
(def totalItemCount 0)


; Loads customer data before printing menu
(defn loadCustomerData [fileName]
  (def lines (clojure.string/split-lines (slurp fileName)))

  ;Reference: https://stackoverflow.com/questions/25948813/read-line-by-line-for-big-files
  ;Reference: https://lethain.com/reading-file-in-clojure/
  (doseq [line lines]
    (def data_by_record (clojure.string/split line #"\|"))
    (def cust_ID_Name_Map (assoc cust_ID_Name_Map (get data_by_record 0) (get data_by_record 1)))
    (def restData (str ":[" (get data_by_record 1) ", " (get data_by_record 2) ", " (get data_by_record 3) "]"))
    (def print_Customer_Info_Map (assoc print_Customer_Info_Map (get data_by_record 0) restData))
    )
  )

; when choice is 1, then display customers data
(defn displayCustomerTable []

  ;Reference: https://clojuredocs.org/clojure.core/sorted-map
  (def print_Customer_Info_Map (into (sorted-map) print_Customer_Info_Map))
  (println "-------------------------------------------------")
  (println "\t\tCUSTOMER TABLE")
  (println "-------------------------------------------------")

  ;Reference: https://stackoverflow.com/questions/6685916/how-to-iterate-over-map-keys-and-values-in-clojure
   (doseq [[k v] print_Customer_Info_Map]
     (println (str k (get print_Customer_Info_Map k))))
  (println "-------------------------------------------------")
  )

; Loads product data before printing menu
(defn loadProductData [fileName]
  (def lines (clojure.string/split-lines (slurp fileName)))
  (doseq [line lines]
    (def data_by_record (clojure.string/split line #"\|"))
    (def prod_ID_Name_Map (assoc prod_ID_Name_Map (get data_by_record 0) (get data_by_record 1)))
    (def prod_ID_Cost_Map (assoc prod_ID_Cost_Map (get data_by_record 0) (get data_by_record 2)))
    (def restData (str ":[" (get data_by_record 1) ", " (get data_by_record 2) "]"))
    (def print_Prod_Info_Map (assoc print_Prod_Info_Map (get data_by_record 0) restData))
    )
  )

;  when choice is 2, then display product data
(defn displayProductTable
  "It will display product table in a format"
  []
  (def print_Prod_Info_Map (into (sorted-map) print_Prod_Info_Map))
  (println "-------------------------------------------------")
  (println "\t\tPRODUCT TABLE")
  (println "-------------------------------------------------")

  ;Reference: https://stackoverflow.com/questions/6685916/how-to-iterate-over-map-keys-and-values-in-clojure
  (doseq [[k v] print_Prod_Info_Map]
    (println (str k (get print_Prod_Info_Map k))))
  (println "-------------------------------------------------")
  )

; Loads sales data before printing menu
(defn loadSalesData [fileName]
  (def lines (clojure.string/split-lines (slurp fileName)))

  ;Reference: https://stackoverflow.com/questions/25948813/read-line-by-line-for-big-files
  ;Reference: https://lethain.com/reading-file-in-clojure/
  (doseq [line lines]
    (def data_by_record (clojure.string/split line #"\|"))
    (def sales_ID_Cust_ID_Map (assoc sales_ID_Cust_ID_Map (get data_by_record 0) (get data_by_record 1)))
    (def sales_ID_Prod_ID_Map (assoc sales_ID_Prod_ID_Map (get data_by_record 0) (get data_by_record 2)))
    (def sales_ID_ItemCount_Map (assoc sales_ID_ItemCount_Map (get data_by_record 0) (get data_by_record 3)))
    (def restData (str ":[" (get cust_ID_Name_Map (get data_by_record 1)) ", " (get prod_ID_Name_Map (get data_by_record 2)) ", " (get data_by_record 3) "]"))
    (def print_Sales_Info_Map (assoc print_Sales_Info_Map (get data_by_record 0) restData))
    )
  )

;  when choice is 3, then display product data
(defn displaySalesTable
  "It will display sales table in a format"
  []
  (def print_Sales_Info_Map (into (sorted-map) print_Sales_Info_Map))
  (println "-------------------------------------------------")
  (println "\t\tSALES TABLE")
  (println "-------------------------------------------------")

  ;Reference: https://stackoverflow.com/questions/6685916/how-to-iterate-over-map-keys-and-values-in-clojure
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
    (def totalSales_By_Product_Map
      (assoc totalSales_By_Product_Map k (* (Float/parseFloat productCost)  (Integer/parseInt itemCount) ))
      )
    )

  (def totalCost 0.0)
  (println "Enter customer name to count total sale : ")
  (def customerName (read-line))

  ;Reference: https://stackoverflow.com/questions/18176372/clojure-get-map-key-by-value
    (def custIDForName (keep #(when (= (val %) customerName) (key %)) cust_ID_Name_Map))

    (println "-----------------------------------------------------")
    (if (empty? custIDForName)
      (do
        (def totalCost 0.0)
        (println "Given customer is not present in the customer table.")
        )
      (doseq [[k v] sales_ID_Cust_ID_Map]
        (if (= (Integer/parseInt v) (Integer/parseInt (apply str custIDForName)))
          (def totalCost (+ totalCost (get totalSales_By_Product_Map k) ) )
          )
        )
      )

    ;Reference: https://clojuredocs.org/clojure.core/with-precision
    (println (str customerName ": $" (format "%.2f" totalCost)))
    (println "-----------------------------------------------------")
    )

; when choice is 5, then calculate and display total count of given product
(defn totalCountForProduct
  "This will return the total count of the product for given customer"
  []
  (println "Enter product name to count total items : ")
  (def productName (read-line))

  ;Reference: https://stackoverflow.com/questions/18176372/clojure-get-map-key-by-value
  (def prodIdForSales (keep #(when (= (val %) productName) (key %)) prod_ID_Name_Map))
  (def totalItemCount 0)
  (println "-------------------------------------------------")

  (if (empty? prodIdForSales)
    (do
      (def totalItemCount 0)
      (println "Given item is not present in the product table.")
      )
    (doseq [[k v] sales_ID_Prod_ID_Map]
      (if (= (Integer/parseInt v) (Integer/parseInt (apply str prodIdForSales)))
        (def totalItemCount (+  totalItemCount (Long/parseLong (get sales_ID_ItemCount_Map k))) )
        )
      )
    )

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
  (let [choice (read-line)]

    ;Reference: https://www.tutorialspoint.com/clojure/clojure_cond_statement.htm
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
    )
  )

(displayMenu)