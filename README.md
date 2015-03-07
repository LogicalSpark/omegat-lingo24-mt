# Lingo24 Premium MT Plugin for OmegaT

This project contains a plugin for the [OmegaT](http://www.omegat.org/) translation memory tool to allow it to source
machine translation options from the [Lingo24 Premium MT API](https://developer.lingo24.com/), taking advantage of their
dedicated vertical engines.

# Usage

In order to make use of this plugin, you must register for a [Lingo24 Premium MT API] (https://developer.lingo24.com/)
account and have an active plan. There are a number of paid plans as well as a free taster plan.

Once you have created an account, you can download the Zip file and extract its contents into your OmegaT installation's
plugins folder.

To use the plugin you must provide OmegaT with the user_key for your Developer Account by adding the following argument
to your OmegaT launch script, replacing the 'XXXX' with your key:
   
   ```
   -Dlingo24.api.key=XXXX
   ```

Finally, in OmegaT you can enable or disable the plugin using **Options > Machine Translate > Lingo24 Premium MT**.
When enabled, machine translations suggestions will appear in the Machine Translation pane of the editor.

# Building

From the top level of the repo run the following command:

   ```
   mvn install
   ```

This will then create two files in the `target` folder; a Jar file containing the plugin, and a Zip file containing the
plugin and licence for distribution.

To use the plugin directly simply copy the `omegat-lingo24-mt-*.jar` to your local OmegaT installations plugin folder.

Otherwise, you can distribute the Zip file to end uses to allow them to unzip the file into their OmegaT installations
plugin folder.

# License

This project is distributed under the [GNU General Public License, v3]
(http://www.gnu.org/licenses/gpl-3.0.html).

This project makes use of the Groovy Json library licenced under the Apache Licence 2.0. This is used here only for
compilation, with the main library included with the full OmegaT package.

Copyright 2015 David Meikle <david@logicalspark.com>

[![Build Status](https://travis-ci.org/LogicalSpark/omegat-lingo24-mt.svg?branch=master)](https://travis-ci.org/LogicalSpark/omegat-lingo24-mt)
