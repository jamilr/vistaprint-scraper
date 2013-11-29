vistaprint-scraper
==================

VistaPrint Business Directory Scraper

Using VistraPrint Business Directory search API, the scraper makes a number of search queries to get lists of existing VistaPrint customers.
Search query is a tuple consisting of a topic and state keyword.

KeywordScraperService is used to populate topic set. It works as supervised method by taking training set of topics and making a number of search API calls.
Upon getting results form the search, it then breaks down descriptions to tokens and puts the tokens to Apache Lucene Indexer.
Using Apache Lucene Indexer adn TF-IDF, KeywordScraperService identifies the top K keywords used in descriptions of customers.
Given that Top K keywords, a new topic set is created and outputed to the text file.

For preventing a scraper to add duplicate customer information, an algorithm uses customers' names. Lucene's NGramDistance class is used. NGramDistance calculates the distance between
two words and thus calculates how close two words are. Given the threshold, an algorithm makes decision if two customer names are similar or different.