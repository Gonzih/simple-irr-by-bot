(ns simple-bot.notify
  (:use [clojure.pprint]
        [clojure.tools.logging :only (info error)])
  (:import org.apache.commons.mail.HtmlEmail))

(defn mailer [body]
  (doto (HtmlEmail.)
        (.setHostName "smtp.gmail.com")
        (.setSslSmtpPort "465")
        (.setSSL true)
        (.setTLS true)
        (.addTo "gonzih@gmail.com")
        (.setFrom "bot@gonzih.org" "gnzh's bot")
        (.setSubject "New ad on irr.by")
        (.setCharset "UTF-8")
        (.setHtmlMsg body)
        (.setAuthentication "gonzih@gmail.com" (get (System/getenv) "GMAIL_PASS"))
        (.send)))


(defn format-body [{:keys [image link title location text price]}]
  (str
    "<img src='" image "'>"
    "<br/>"
    "<a href='" link "'>"
      title
    "</a>"
    "<br/>"
    "Text: " text
    "<br/>"
    "Price: " price
    "<br/>"
    "Location: " location
    "<br/>"
    "<br/>"))

(defn items [is]
  (info "Sending emails for " (count is) " items")
  (mailer (apply str (map format-body is))))
