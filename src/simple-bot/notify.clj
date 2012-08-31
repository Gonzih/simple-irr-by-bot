(ns simple-bot.notify
  (:use [clojure.pprint]
        [mmemail.core])
  (:import org.apache.commons.mail.HtmlEmail))

(defn mailer [body]
  (doto (HtmlEmail.)
        (.setHostName "smtp.gmail.com")
        (.setSslSmtpPort "465")
        (.setSSL true)
        (.setTLS true)
        (.addTo "gonzih@gmail.com")
        (.setFrom "bot@gonzih.org" "gnzh's bot")
        (.setSubject "New post on irr.by")
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
    "Location: " location))

(defn item [i]
  (mailer (format-body i)))

(defn items [is]
  (doall (map item is)))
