(ns natural-deduction.core
  (require 
    [clojure.core.logic :refer :all :exclude [==]]
    [clojure.walk :refer :all]))

(load "scope")
(load "apply-rule")
(load "natdec-rules")
