(ns simple-bot.notify
  (:use [clojure.pprint]
        [clojure.tools.logging :only (info error)])
  (:import org.apache.commons.mail.HtmlEmail))

(def target-emails []
  "Emails should be in EMAILS env var, like 'one@email.com;second@email.com'"
  (-> (System/getenv) (get "EMAILS") (clojure.string/split #";")))

(defn setup-and-send-email [^HtmlEmail mail body]
  (doseq [email (target-emails)]
    (.addTo mail email))
  (doto mail
        (.setHostName "smtp.gmail.com")
        (.setSslSmtpPort "465")
        (.setSSL true)
        (.setTLS true)
        (.setFrom "bot@gonzih.org" "gnzh's bot")
        (.setSubject "New ad on irr.by")
        (.setCharset "UTF-8")
        (.setHtmlMsg body)
        (.setAuthentication "gonzih@gmail.com" (get (System/getenv) "GMAIL_PASS"))
        (.send)))

(defn mailer [body]
  (setup-and-send-email (HtmlEmail.) body))

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
