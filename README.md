# OTO Generator

This generator creates diphone OTOs arpasing-style from an index.csv, allowing you to set suffixes, base values, and some other options.  Full details about the features are below.

To use, download OtoAliaser.zip and decompress.<br>
Put an index.csv (and optionally a replace.csv) in the "csv" folder, then run the "OtoGenerator.jar" file.<br>
There are CSVs available here for generating a basic Japanese CVVC oto.

<hr>

Things both versions can do
- generate aliases arpasing-style from index.csv
- add suffixes
- user settable oto base values
- optional start/end aliases
- find+replace aliases after generation from replace.csv

Things the java version can do
- has an ugly swing gui
- works as a standalone application on all computers
- number duplicate aliases up to a maximum and delete excess

Things the python3 version can do
- has an ugly tkinter gui
- has all strings at beginning, for translators
- has an esperanto translation, just because
- number or delete all duplicate aliases

Things this will do
- different oto base values based on phonemes (user settable)

Things this won't do
- fancy audio analysis timing adjustment
