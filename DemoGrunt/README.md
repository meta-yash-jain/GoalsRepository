# grunt-sass-starter
A super simple Grunt with Sass starter example with JSHint, livereload and Source Maps ready to roll

### Requirements ##
*   Install [Ruby](https://www.ruby-lang.org/en/) & [Node.js](https://nodejs.org/en/) on your computer
*   Install Grunt in command line via `npm install -g grunt-cli`
*   Install [Sass](http://sass-lang.com/install)

### Installation
1. Copy and paste both the **package.json** and **Gruntfile.js** into your project directory
2. In command line navigate to your **project directory** and run `npm install`
3. In your base index.html file, link up the files as per the **Files** section below
4. Run `grunt watch`
5. Code away via **app.scss**

### File structure

Here's the base files you need. Livereload is optional but if you choose not to include you'll need to remove the entire `livereload` section of your Gruntfile.js.

#### CSS
`<link rel="stylesheet" href="assets/build/app.min.css">`

#### JS
`<script type="text/javascript" src="assets/build/app.min.js"></script>`

#### Livereload (optional)
`<script src="http://localhost:35729/livereload.js"></script>`

You can find heaps more handy code snippets on my blog at [andycrone.com](https://andycrone.com/)
