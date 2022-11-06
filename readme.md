# Generative art playground

A ClojureScript generative art playground, with a repl and hot reloading support.

**Prerequisites**

- Install packages for docker using your favorite package manager
- Install colima or docker desktop, to use as container runtime
- Start colima or docker desktop

## Setup: VSCode: hot reload on save + browser repl for live code evaluation

**Step 1**

- Clone and open the repo in vscode

```sh
git clone git@github.com:torgeir/cljs-workshop.git && cd cljs-workshop && code .
```

**Step 2**

- Press "Reopen in container" in the lower right corner

**Step 3**

When you are greeted by Calva

- Open `src/sketches/init.cljs`.
- Hit `cmd + shift + p` and start type `jack`, hit enter.
- Select `deps.edn + Figwheel Main`, hit enter

**Step 4**

When you see the following output in the `terminal` tab

> [Figwheel] figwheel-main.edn is valid \(ツ)/
> [Figwheel] Compiling build dev to "target/public/cljs-out/dev-main.js"
> ...
> [Figwheel] Starting Server at http://localhost:9500

- Open the url in your browser

**Step 5**

Wait until the green "clj" switches to "cljs" or "cljc/cljs" in the bottom row of vscode.

You are connected to an in-browser repl, and can evaluate cljs code on the fly!

**Try it*

- Type `(+ 1 2)` and place the cursor behind the last paren.
- `cmd + shift + p` and type `Calva: Evaluate current form`.
- You should see `=> 3`.

## Alternate setup: VSCode: hot reload on save

Run the workshop

```
docker run -it -p9500:9500 -p4000:4000 torgeir/cljs-workshop
```

It will say it can't open the browser, do it yourself:

Open [localhost:9500](http://localhost:9500) in the browser.

To be able to edit files inside the docker container:

- Click the Extensions tab in VSCode
- Install the `Remote - Containers` extension and the `Calva: Clojure & Clojurescript Interactive Programming` extension
- A new icon `Remote Explorer` on the left shows running docker containers
- Right click on the container `torgeir/cljs-workshop` and choose `Connect to container`
- When the new window opens, click `Open folder` and enter `/root/cljs-workshop`

You are ready to edit sketches!

## Manual setup

If all else fails:

Clone the repo and run

```
clojure -M -m figwheel.main -b dev
```

### Run it, with a REPL on steriods inside your favourite editor

(when not using the docker approach)

#### VSCode

Install `Calva: Clojure & Clojurescript Interactive Programming`

Open `src/sketches/init.cljs`.

Hit `cmd + shift + p` and start type `jack` and hit enter.

Select `deps.edn + Figwheel Main`, hit enter, wait, select `dev`, hit enter.

Evaluate something in the browser from your editor with `ctrl+alt+c e`.

#### Emacs

Install `cider`.

Run `cider-jack-in-cljs`

Evalute something with `C-c C-c`

## Installing clojure

**Not needed if you have colima/docker.**

- Install a minimum of [java 8](https://adoptopenjdk.net/)

### MacOS

Installer homebrew

```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

Install clojure

```sh
brew install clojure/tools/clojure
```

### Linux

**outdated, see https://clojure.org/guides/install_clojure**

#### Homebrew

```sh
brew install clojure/tools/clojure
```

#### Manually

Don't use `apt install clojure` if you are on ubuntu, it somehow messes up the figwheel initialization process later.

Make sure you have the following dependencies installed `bash`, `curl` and `rlwrap`.

Run the following:

```sh
curl -O https://download.clojure.org/install/linux-install-1.11.1.1182.sh
chmod +x linux-install-1.11.1.1182.sh
sudo ./linux-install-1.11.1.1182.sh
```

It creates `/usr/local/bin/clj`, `/usr/local/bin/clojure`, and `/usr/local/lib/clojure`.

Run it with `./linux-install-1.11.1.1182.sh --prefix somewhere/else/` if you want the files installed to `somewhere/else/` instead.

### Windows

**outdated, see to https://clojure.org/guides/install_clojure and https://github.com/clojure/tools.deps.alpha/wiki/clj-on-Windows**

## Build for dockerhub

Given your dockerhub username is `torgeir`, login

```sh
docker login -u torgeir
```

Build and deploy

```sh
docker build . -t torgeir/cljs-workshop:latest
docker push torgeir/cljs-workshop:latest
```

It deploys to https://hub.docker.com/repository/docker/torgeir/cljs-workshop

## Resources

- Cheat sheet
https://cljs.info/cheatsheet/

- Clojure quick start
https://clojurescript.org/guides/quick-start

- Color palettes
https://kgolid.github.io/chromotome-site/

- Quil API http://quil.info/api
