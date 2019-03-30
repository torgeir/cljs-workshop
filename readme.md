# Minimal `cljs` playground with repl and hot reloading support

## Install

- Install [java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)


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

Open `Settings -> Extensions -> Calva -> Calva: Start CLJSREPLCommand`, paste the following and save it.

```
(do
  (require '[cider.piggieback :refer [cljs-repl]]
           '[figwheel.main.api :refer [repl-env]])
  (cljs-repl (repl-env "dev") :output-dir "target/public/cljs-out/dev"))
```

Restart VSCode.

Evaluate something in the browser from your editor with `ctrl+alt+v e`.

### Emacs

Install cider.

Run `cider-jack-in-cljs`.

## Resource

https://cljs.info/cheatsheet/

https://clojurescript.org/guides/quick-start
