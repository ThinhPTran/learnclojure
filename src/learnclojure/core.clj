(ns learnclojure.core
  (:gen-class)
  (:import (clojure.java.api Clojure))
  (:require [clojure.string :as str]
            [inflections.core :as inf])) 


                                        ;Clojure Cookbook

;; Say Hello
(defn sayHello [x]
  (str "Hello " x))

(sayHello "Thinh")

;; Check blank string
(str/blank? "")

;; Capitalize the first character in a string
(str/capitalize "this is a proper sentence.")

;; Uppercase
(str/upper-case "loud noises!")

;; lowercase
(str/lower-case "COLUMN_HEADER_ONE")

;; trim
(str/trim " \t Bacon ipsum dolor sit. \n")

;; Collapse whitespace into a single space
(str/replace "Who\t\nput  all this\fwhitespace here?" #"\s+" " ")

;; Replace Window-style line endings with Unix-style newlines
(str/replace "Line 1\r\nLine 2" "\r\n" "\n")

;; Trim left
(str/triml "  Column header\t")

;; Trim right
(str/trimr "\t\t* Second-level bullet.\n")

;; apply str
(apply str ["a" "b" "c"]) ;; is functionally equivalent to

;; Constructing a CSV from a header string and vector of rows
(def header "first_name, last_name, employee_number")
(def rows ["luke,vanderhart,1" "ryan, neufeld, 2"])
(def contents (apply conj [header] rows))

(interpose "\n" rows)("luke,vanderhart,1" "\n" "ryan, neufeld, 2")
(apply str header "\n" (interpose "\n" rows))
(str/join "\n" contents)
 
(def food-items ["milk" "butter" "flour" "eggs"])
(str/join ", " food-items)

;; Sequence from a string
(seq "Hello, world!")

;; Count the occurences of each character in a string
(frequencies (str/lower-case "An adult all about A's"))

;; Is every letter in a string capitalized?
(defn yelling? [s]
  (every? #(or (not (Character/isLetter %))
               (Character/isUpperCase %)) s))

(yelling? "LOUD NOISES!")

(yelling? "Take a DEEP breath.")

;; Convert char to int
(int \a)
(int \A)

(map int "Hello, world!")

;; Convert int to char
(char 97)
(char 248)
(char 945)

;; Use reduce with two args
(reduce #(str %1 (char %2))
        " "
        [115 101 99 114 101 116 32 109 101 115 115 97 103 101 115])

;; Using format
(defn filename [name i]
  (format "%03d-%s" i name))

(filename "my-awesome-file.txt" 42)

;; Create a table using justification
(defn tableify [row]
  (apply format "%-20s | %-20s | %-20s" row))

(def header ["First Name", "Last Name", "Employee ID"])
(def employees [["Ryan", "Neufeld", 2] ["Luke", "Vanderhart", 1]])

(->> (concat [header] employees)
     (map tableify)
     (mapv println))

(->> (concat [header] employees)
     (mapv tableify)
     (str/join "\n"))

;; Search String Pattern
;; Any contigous groups of numbers
(re-find #"\d+" "I've just finished reading Fahrenheit 451")
(re-find #"Bees" "Beads aren't cheap")
(re-find #"\w+" "my-param")

;; Using re-matches
(re-matches #"\w+" "my-param")
(re-matches #"\w+" "my")

;; Extract portions of a string matching a given pattern
(re-seq #"\w+" "My Favorite Things")

;; Extract a simple 7-digit phone numb4ers
(re-seq #"\d{3}-\d{4}" "My phone number is 555-1234")

;; Extract all of the Twitter usernames and hashtags in a tweet
(defn mentions [tweet]
  (re-seq #"(@|#)(\w+)" tweet))

(mentions "So long, @earth, and thanks for all the #fish. #goodbyes") 

(def about-me "My favorite color is green!")
(str/replace about-me "green" "red")

;; Linkify-comment
(defn linkify-comment
  "Add Markdown-style links for any GitHub issue numbers present in comment"
  [repo comment]
  (str/replace comment
               #"#(\d+)"
               (str "[#$1](https://github.com/" repo "/issues/$1)")))

(linkify-comment "next/big-thing" "As soon as we fix #42 and #1337 we should be set to release!")

;; Split String into a number of parts
(str/split "HEADER1, HEADER2, HEADER3" #",")
(str/split "Spaces Newlines\n\n" #"\s+")
(str/split "field1     field2 field3   " #"\s+")
(str/split "field1     field2    field3    " #"\s+" -1)

(def data-delimiters #"[ :-]")
(str/split "2013-04-05 14:39" data-delimiters )
(str/split "2013-04-05 14:39" data-delimiters 1)
(str/split "2013-04-05 14:39" data-delimiters 2)
(str/split "2013-04-05 14:39" data-delimiters 100)

;; Try inflections
(inf/pluralize 1 "monkey")
(inf/pluralize 12 "monkey")
(inf/pluralize 1 "box" "boxen")
(inf/pluralize 3 "box" "boxen")

;; Convert from "snake_case" to "CamelCase"
(inf/camel-case "my_object")
(inf/parameterize "My most favorite URL!")
(inf/ordinalize 42)

;; Convert from a string to symbol
(symbol "valid?")

;; Convert from a symbol to a string
(str 'valid?)

;; Convert a keyword into a name
(name :triumph)

;; Including the leading colon:
(str :triumph)

;; Convert from a symbol or string to a keyword
(keyword "fantastic")
(keyword 'fantastic)

;; Get the name part of a keyword
(name :user/valid?)

;; if you only want the namespace
(namespace :user/valid?)

;; Get both
(str :user/valid?)
(.substring (str :user/valid?) 1)

;; Convert namespaced symbols to keywords
(keyword 'produce/onions)

;; And vice versa
(symbol (.substring (str :produce/onions) 1))

;; Both keyword and symbol functions have two-argument versions that allow you to pass in the namespace and name separately.
(def shopping-area "bakery")
(keyword shopping-area "bagels")
(symbol shopping-area "cakes")




;Bindings
;Giving names to values is called assignment in many programming languages.
;However, we call the mapping between names and values binding in Clojure.

;Loop in Clojure
;recur

(defn power
  ([x y] (power x y 1))
  ([x y current]
   (if (= y 0)
     current
     (if (> y 0)
       (recur x (- y 1) (* x current))
       (recur x (+ y 1) (/ current x))))))

(defn counttoN
  [n]
  (loop [count n
         current 0]
    (if (= count 0)
      current
      (recur (- count 1) (+ current count)))))




(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
