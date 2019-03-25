# Minimal `figwheel-main` playground

## Install

### OS X

```sh
brew update
brew install clojure
```

### Windows

Use the provided `.\clj.exe` as a replacement for the `clj` command.

## REPL on steroids

```
clj -Arepl
```

### VSCode

Install extensions

- Calva: Clojure & Clojurescript Interactive Programming
- Calva Clojure Formatter
- Calva Paredit

Open `Settings -> Extensions -> Calva -> Calva: Start CLJSREPLCommand`, paste
the following `(do (require '[cider.piggieback :refer [cljs-repl]]
'[figwheel.main.api :refer [repl-env]]) (cljs-repl (repl-env "dev")))`, and save it.

### Emacs

Install cider.

Run `cider-jack-in-cljs`.

## Resource

https://cljs.info/cheatsheet/

https://clojurescript.org/guides/quick-start
