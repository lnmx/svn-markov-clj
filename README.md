
# svn-markov-clj

A simple example program that reads Subversion history, builds a markov (chain)
model from the commit messages, and generates new messages from the model.

Requires [clojure](http://clojure.org/) and [leiningen](http://leiningen.org/)

Extract the subversion history in XML format from your local repo:

    svn log /my/svn/repo > history.xml

... or from Subversion's public repo:

    svn log -l 1000 --xml http://svn.apache.org/repos/asf/subversion/trunk > history.xml

Run the program with `lein run`:

    $ lein run
    loaded history.xml with 1000 commits
    ready

Press Enter to generate a new commit message:

    => Tired of deleting children before marking ourself complete.

    => sizeof() was returning with Forbidden 403, as tried to support Perl SWIG bindings on windows.

Exit with `^C`.
