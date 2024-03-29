# Generative art playground

A ClojureScript generative art playground, with a repl and hot reloading support.

**Prerequisites**

- Install packages for docker using your favorite package manager
- Install colima or docker desktop, to use as container runtime. E.g. [like this](https://hjerpbakk.com/blog/2022/02/01/replacing-docker-desktop) on a mac
- Start colima or docker desktop

## Recommended setup: VSCode: Using the devcontainer

**Step 1**

- Clone and open the repo in vscode

```sh
git clone https://github.com/torgeir/cljs-workshop.git && cd cljs-workshop && code .
```

**Step 2**

- Click "Install" when prompted to install the "Dev Containers" extension.
- Click "Reopen in container" when prompted to open the repo's dev container.

**Step 3**

When you are greeted by Calva

- Open `src/help/tutorial.cljs`.
- Hit `cmd + shift + p` and start type `start project repl`, hit enter.
- Select `deps.edn + Figwheel Main`, hit enter

**Step 4**

Wait through the following, and ignore the prompt asking to open port localhost:4000..

> [Figwheel] figwheel-main.edn is valid \(ツ)/
> 
> [Figwheel] Compiling build dev to "target/public/cljs-out/dev-main.js"
> 
> ...
> 
> [Figwheel:WARNING] ...

When you see this, or are prompted to open it

> Starting Server at http://localhost:9500

- Click to open the url in your browser

**Step 5**

Wait until the green `clj` switches to `cljs` or `cljc/cljs` in the bottom row of vscode.

_**You are connected to an in-browser repl, and can evaluate cljs code on the fly!**_

**Try it**

- Place the cursor behind `(+ 1 2)`.
- Evaluate something!
  - Hit `control+enter`, or
  - `cmd + shift + p` and type "evaluate", and select `Calva: Evaluate current form`

What just happened?

- You compiled clojurescript code to js.
- Sent it to the browser.
- Evaluated it and got a result back.
- That was sent to you editor and displayed inline.
- State you keep will persist until the browser window is closed.

## Alternate docker setup: Connecting to docker yourself

Run the workshop

```
docker run -it -p9500:9500 -p4000:4000 torgeir/cljs-workshop
```

It will say it can't open the browser, do it yourself:

Open [localhost:9500](http://localhost:9500) in the browser.

To be able to edit files inside the docker container, if you're in VSCode:

- Click the Extensions tab
- Install the `Remote - Containers` extension and the `Calva: Clojure & Clojurescript Interactive Programming` extension
- A new icon `Remote Explorer` on the left shows running docker containers
- Right click on the container `torgeir/cljs-workshop` and choose `Attach to container`

You are ready to edit sketches!

## Alternate manual setup 1: You already have java and clojure

### VSCode

Install `Calva: Clojure & Clojurescript Interactive Programming`

Hit `cmd + shift + p` and start type `jack` and hit enter.

Select `deps.edn + Figwheel Main`, hit enter, wait.

Evaluate something in the browser from your editor with `control+enter`.

### Emacs

Install `cider`.

Run `cider-jack-in-cljs`, select `figwheel-main`, select the `dev` build.

Evalute something with `C-c C-c`.

When your done, `cider-quit`.

## Alternate manual setup 2: If all else fails

- Clone the repo
- Install java and clojure
- Run this

```
clojure -M -m figwheel.main -b dev
```

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
docker build --no-cache . -t torgeir/cljs-workshop:latest
docker push torgeir/cljs-workshop:latest
```

It deploys to https://hub.docker.com/repository/docker/torgeir/cljs-workshop

## Troubleshooting

If jack in fails with an error like this, make sure you gave colima/docker desktop enough cpu and memory, the defaults are pretty low.

```
> Killed
> Jack-in process exited. Status: 137

colima stop
colima start -c 4 -m 16
```

## Resources

- Cheat sheet
https://cljs.info/cheatsheet/

- Clojure quick start
https://clojurescript.org/guides/quick-start

- Color palettes
https://kgolid.github.io/chromotome-site/

- Quil API http://quil.info/api

- More on Cljs
https://practical.li/clojurescript/

- Generative art using perlin noise explained
https://www.bekk.christmas/post/2019/13/functional-generative-art-using-clojurescript

