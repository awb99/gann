(ns gann.flo
  (:require [reagent.core :as reagent :refer [atom]]
      [pointslope.remit.events :as pse :refer [emit subscribe]]
      [pointslope.remit.middleware :as psm :refer [event-map-middleware]]
      [reagent.core :as r]
 ))

; http://jonibologna.com/svg-viewbox-and-viewport/
; https://www.mattgreer.org/articles/embedding-svg-into-a-reagent-component/
; https://github.com/awb99/reagent-svg-demo/blob/master/src/cljs/svg/app.cljs
; https://github.com/awb99/tictactoe/blob/master/src/tictactoe/core.cljs
; https://github.com/thi-ng/geom/
;https://github.com/oakes/play-cljs-examples/blob/master/super-koalio/src/super_koalio/core.cljs

(def size 500)

(defn circle [i j]
  [:circle
   {:r 0.35
    :stroke "green"
    :stroke-width 0.12
    :fill "none"
    :cx (+ 0.5 i)
    :cy (+ 0.5 j)}])

(defn cross [i j]
  [:g {:stroke "darkred"
       :stroke-width 0.4
       :stroke-linecap "round"
       :transform
       (str "translate(" (+ 0.5 i) "," (+ 0.5 j) ") "
            "scale(0.3)")}
   [:line {:x1 -1 :y1 -1 :x2 1 :y2 1}]
   [:line {:x1 1 :y1 -1 :x2 -1 :y2 1}]])


(defonce app-state
  (atom {:text "Welcome to tic tac toe"
         :game-status :in-progress}))

;(defn update-status [state]
;  (assoc state :game-status (game-status (:board state))))


(defn select-component
  [fill]
  (fn [fill]
    (let [colors ["gray" "red" "orange" "yellow" "green" "blue"
                  "aqua" "indigo" "purple" "brown" "black"]]
      (do
        [:div {:class "form-group"}
         [:label {:for "Color"} "Color"]
         [:select {:name "Color"
                   :class "form-control"
                   :value @fill
                   :on-change (fn [e] (emit :color-changed
                                           {:color (->> e .-target .-value)}))}
          (for [c colors]
[:option {:value c :key c} c])]]))))



(defn range-component
  [v min max title]
  [:div {:class "form-group"}
   [:label {:for title} title [:small " [ " @v " ] "]]
   [:input {:type "range"
            :class "form-control"
            :name title
            :key title
            :min min
            :max max
            :value @v
            :on-change (fn [e] (emit :dimension-changed
{:cursor v :value (-> e .-target .-value)}))}]])


(defonce app-db (atom {:x 360 :y 200 :radius 50 :fill "black" :clicks 0}))

(defn fib-line [y x width]
      [:line { :y1 y :y2 y
               :x1 x  :x2 (+ x width)  
               :style {:stroke "rgb(255,0,0)" :stroke-width 2}}])


(defn lines []
  [:g
    [:line {:x1 100 :y1 100 :x2 500 :y2 100 :style {:stroke "rgb(255,0,0)" :stroke-width 2}}]
    [:line {:x1 100 :y1 200 :x2 500 :y2 200 :style {:stroke "rgb(255,0,0)" :stroke-width 2}}]])


(defn fib-sr [x width]
  "fibonacci support-resistence"
  (let [fibs [3.6 2.6 1.6 1.0 0.62 0.36]]
    (fn [] 
      [:g
        (for [y fibs] [fib-line (* 100 y) x width ])
      ])))
 
 
 ; [:g
 ;     [:line {:x1 100 :y1 100 :x2 500 :y2 100 :style {:stroke "rgb(255,0,0)" :stroke-width 2}}]
 ;     [:line {:x1 100 :y1 200 :x2 500 :y2 200 :style {:stroke "rgb(255,0,0)" :stroke-width 2}}]
 ; ]
 
 
 


(defn demo []
 (let [x (r/atom 20)
       fill (r/atom "red")
       view-size 400
       size 600
 ]
  (fn [] 
    [:center
      [:svg {:x 0 :y 0 
             :width size :height size 
             :view-box (str "0 0 " view-size " " view-size)
             :style {:background-color "gray"}
             }
        [:rect {:x 10 :y 10 :width 10 :height 10}]
        [:rect {:x 100 :y 50 :width 30 :height 30}]
        [:rect {:x 200 :y 50 :width 30 :height 30 :style {:fill "red"}}]
        [:rect {:x 300 :y 10 :width 30 :height 30 :style {:fill "blue"}}]
        [:circle
          {:r 0.35
            :stroke "green"
            :stroke-width 500
            :fill "none"
            :cx 100
            :cy 150
            }]
        [fib-sr 50 50]
        [fib-sr 200 50]
        [fib-sr 300 50]
      ]
      [range-component x 0 720 "CX"]
      [select-component fill]
      ]
      )))
     



(defn demo2 []       
  (let [x (r/atom 20)]
  (fn [] 
    [:center

      [:svg
         {:view-box (str "0 0 " size " " size)
          :width 1000
          :height 1000}
        [circle 3 3]
        [circle 2 2]    
        [cross 1 1]
      ]
       [range-component x 0 720 "CX"]
      
  ])))
