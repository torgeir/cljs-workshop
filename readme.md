# Minimal `cljs` playground with repl and hot reloading support

## Install

- Install [java 8](https://adoptopenjdk.net/)


### OS X

```sh
brew update
brew install clojure
```

### Windows

Use the provided `.\clj.exe` as a replacement for the `clj` command.

## REPL on steroids

### VSCode

Install `Calva: Clojure & Clojurescript Interactive Programming`

Hit `cmd + shift + p` and start type `jack` and hit enter.

Select `Clojure CLI + Figwheel Main`, select `:repl`, wait, select `dev`.

Evaluate something in the browser from your editor with `ctrl+alt+c e`.

### Emacs

Install `cider`.

Run `cider-jack-in-cljs`.

### Manually
```
clj -Arepl
```

## Resource

https://cljs.info/cheatsheet/

https://clojurescript.org/guides/quick-start
