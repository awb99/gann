(ns comp.mui

  (:refer-clojure :exclude [list])
  (:require [material-ui]
            [goog.object :as gobj]
            [clojure.string :as s]
            [reagent.core :as r]
            [camel-snake-kebab.core :refer [convert-case]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            ))

(comment (= (keyword->PasCamelCase :kissa-metso) :kissaMetso))
(comment (= (keyword->PasCamelCase :Kissa-metso) :KissaMetso))
(defn keyword->PasCamelCase
  "Converts keywords to PascalCase or camelCase
  respecting case of the first character."
  [kw & rest]
  (keyword (convert-case identity s/capitalize "" (name kw) rest)))

(def create-mui-theme (gobj/get js/MaterialUI "createMuiTheme"))

(defn ->mui-theme [opts]
  (->> opts
       (transform-keys keyword->PasCamelCase)
       clj->js
       create-mui-theme))

(comment (get-color "blue"))
(comment (get-color :blue "300"))
(defn get-color
  "Args can be strings or keywords. Returns all colors if no args are given.
  (get-color)
  (get-color \"blue\")
  (get-color :blue \"300\")"
  [& args]
  (->> args
       (mapv name)
       (apply (partial gobj/getValueByKeys js/MaterialUI "colors"))))

(def primary "#002957")
(def secondary "#f1563f")
(def secondary2 "rgba(241, 86, 63, 0.9)")
(def gold "#C29A5B")
(def gray1 "rgba(199, 201, 200, 1.0)")
(def gray2 "rgba(199, 201, 200, 0.5)")
(def gray3 "rgba(199, 201, 200, 0.3)")

(def headline-aleo
  {:font-family    "Aleo, serif"
   :font-weight    700
   :letter-spacing "+0.025em"
   :text-transform :uppercase})

(def headline-common
  {:font-family    "Lato, serif"
   :font-weight    700
   :text-transform :uppercase})

(def jyu-styles-dark
  {:typography
   {:font-family "Lato, sans-serif"
    :headline    headline-common
    :display1    headline-common
    :display2    headline-common
    :display3    headline-common
    :display4    headline-common
    :title       headline-common
    :body1
                 {:font-weight    400
                  :line-height    1.4
                  :letter-spacing "-0,025em"}
    :body2
                 {:font-weight    700
                  :line-height    1.4
                  :letter-spacing "-0,025em"}
    :button
                 {:font-weight 700}}
   :palette
   {:type      "dark"
    :primary   {:main primary}
    :secondary {:main secondary}
    :text      {:disabled "rgba(0, 0, 0, 0.68)"}}
   :overrides
   {:Mui-card-header
    {:title
     {:font-size "2rem"}
     :action
     {:margin-top 0}}}})


(def jyu-theme-dark (->mui-theme jyu-styles-dark))

(defn mui->reagent [mui-name]
  (r/adapt-react-class (gobj/get js/MaterialUI mui-name)))

(def css-baseline (mui->reagent "CssBaseline"))
(def mui-theme-provider (mui->reagent "MuiThemeProvider"))
(def app-bar (mui->reagent "AppBar"))
(def tool-bar (mui->reagent "Toolbar"))
(def typography (mui->reagent "Typography"))

(def icon (mui->reagent "Icon"))
(def svg-icon (mui->reagent "SvgIcon"))
(def icon-button (mui->reagent "IconButton"))


(def text-field (mui->reagent "TextField"))

(def grid (mui->reagent "Grid"))
(def paper (mui->reagent "Paper"))
(def card (mui->reagent "Card"))
(def card-content (mui->reagent "CardContent"))
(def card-header (mui->reagent "CardHeader"))
(def card-actions (mui->reagent "CardActions"))
(def card-media (mui->reagent "CardMedia"))
(def chip (mui->reagent "Chip"))

(def menu (mui->reagent "Menu"))
(def menu-item (mui->reagent "MenuItem"))
(def menu-list (mui->reagent "MenuList"))


(def list (mui->reagent "List"))
(def list-item (mui->reagent "ListItem"))
(def list-item-icon (mui->reagent "ListItemIcon"))
(def list-item-text (mui->reagent "ListItemText"))
(def list-item-secondary-action (mui->reagent "ListItemSecondaryAction"))


(def drawer (mui->reagent "Drawer"))
(def divider (mui->reagent "Divider"))
(def swipeable-drawer (mui->reagent "SwipeableDrawer"))

(def tabs (mui->reagent "Tabs"))
(def tab (mui->reagent "Tab"))

(def input-adornment (mui->reagent "InputAdornment"))
(def form-control (mui->reagent "FormControl"))
(def form-control-label (mui->reagent "FormControlLabel"))
(def form-label (mui->reagent "FormLabel"))
(def form-group (mui->reagent "FormGroup"))
(def form-helper-text (mui->reagent "FormHelperText"))
(def button (mui->reagent "Button"))
(def hidden (mui->reagent "Hidden"))

;(def popover (r/adapt-react-class (aget js/MaterialUI "Popover")))
(def popover (mui->reagent "Popover"))
(def tooltip (mui->reagent "Tooltip"))
(def avatar (mui->reagent "Avatar"))
(def checkbox (mui->reagent "Checkbox"))


(def table (mui->reagent "Table"))
(def table-head (mui->reagent "TableHead"))
(def table-body (mui->reagent "TableBody"))
(def table-row (mui->reagent "TableRow"))
(def table-cell (mui->reagent "TableCell"))
(def table-sort-label (mui->reagent "TableSortLabel"))


(def dialog (mui->reagent "Dialog"))
(def dialog-title (mui->reagent "DialogTitle"))
(def dialog-content (mui->reagent "DialogContent"))
(def dialog-actions (mui->reagent "DialogActions"))


(def snackbar (mui->reagent "Snackbar"))
(def snackbar-content (mui->reagent "SnackbarContent"))


(def expansion-panel (mui->reagent "ExpansionPanel"))
(def expansion-panel-actions (mui->reagent "ExpansionPanelActions"))
(def expansion-panel-details (mui->reagent "ExpansionPanelDetails"))
(def expansion-panel-summary (mui->reagent "ExpansionPanelSummary"))


(def input-label (mui->reagent "InputLabel"))
(def select (mui->reagent "Select"))
(def slide (mui->reagent "Slide"))
(def zoom (mui->reagent "Zoom"))
(def fade (mui->reagent "Fade"))
(def grow (mui->reagent "Grow"))

(def with-width* (.withWidth js/MaterialUI))