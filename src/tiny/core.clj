(ns tiny.core)

(def alphabet "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

(defn num->base62 [^int x]
  "Takes an integer and convert it to a Base62 encoded string"
  (letfn [(lambda-fn [n q]
    (if (zero? q) n
      (recur (conj n (nth alphabet (rem q 62))) (quot q 62))))]
    (apply str (lambda-fn '() x))))