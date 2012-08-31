(ns simple-bot.fetch
  (:require [net.cgrand.enlive-html :as html]
            [digest]))

(def domain "http://grodno.irr.by")
(def base-uri "http://grodno.irr.by/realestate/longtime/search/offertype=%D0%BF%D1%80%D0%B5%D0%B4%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5/currency=USD/rooms=2/page_len100/")
(def selector #{[:.tdImg :img] [:.tdTxt :.h3 :a] [:.tdTxt :p] [:.tdPrise :.priceUSD] [:.tdTxt :span.location]})

(defn fetch-data [uri]
  (html/html-resource (java.net.URI. uri)))

(defn parse-data [data]
  (html/select data selector))

(defn split-results [results]
  (partition (count selector) results))

(defmacro attr-with-domain [elem attr]
  `(str domain (first (html/attr-values ~elem ~attr))))

(defn format-result [result]
  (let [[image link text price location] result
        text     (html/text text)
        location (html/text location)
        price    (html/text price)
        title (html/text link)]
    {:image (attr-with-domain image :src)
     :link (attr-with-domain link :href)
     :title title
     :location location
     :text text
     :price price
     :hash (digest/md5 (str text location price))}))

(defn format-results [results]
  (map format-result results))

(defn results []
  (-> base-uri
      fetch-data
      parse-data
      split-results
      format-results))
