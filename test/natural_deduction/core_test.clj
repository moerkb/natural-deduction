(ns natural-deduction.core-test
  (:require [clojure.test :refer :all]
            [natural-deduction.core :refer :all]))

(load "scope_test")
(load "world_test")
(load "apply_rule_test")

(run-all-tests)
