(ns natural-deduction.core-test)

(deftest build-world-test
  (let [a {:body 'a, :hash 1, :rule :premise}
        b {:body 'b, :hash 2, :rule :premise}
        c {:body 'c, :hash 3, :rule nil}]
    
    (testing "Build World"
             (is (= [a] (build-world '[a])))
             (is (= [a b] (build-world '[a b])))
             (is (= [a [b]] (build-world '[a [b]])))
             (is (= [a {:body :todo, :hash 2, :rule nil} c] (build-world '[a ⊢ c]))))))

(deftest build-pretty-string-test
  (testing "Build Pretty String"
           (is (= "a\t(#1\t:premise)" (build-pretty-string {:body 'a, :hash 1, :rule :premise})))))

(deftest set-world!-test
 (let [a {:body 'a, :hash 1, :rule :premise}
        b {:body 'b, :hash 2, :rule :premise}
        c {:body 'c, :hash 3, :rule nil}]
   (testing "Set World"
            (set-world! '[a])
            (is (= @worlds [[a]]))
            
            (set-world! '[a b])
            (is (= @worlds [[a b]]))
            
            (set-world! '[a [b]])
            (is (= @worlds [[a [b]]]))
            
            (set-world! '[a ⊢ c])
            (is (= @worlds [[a {:body :todo, :hash 2, :rule nil} c]])))
   
   ; clean up
   (reset! worlds [])))

(deftest undo!-test
  (let [a {:body 'a, :hash 1, :rule :premise}
        b {:body 'b, :hash 2, :rule :premise}]
    (testing "Undo!"
             (reset! worlds [[a] [a b]])
             (undo!)
             (is (= @worlds [[a]])))
    
    ; clean up
    (reset! worlds [])))
             