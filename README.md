ForumsParser
============

Parser for forums websites

Program used to crawl discussion forums using the Jsoup API.  It extracts all post from the forum and store them in a CSV File.

Usage : 

To parse a discussion forums and extract data, the program needs some parameters :

  a page web of this forum which contains posts,
  an example of posts:
    the author of the post
    the date on which the post was posted
    the message.
  a CSV file name , the file where the posts will be stored,
  The forum URL,
  The common part of all URLs of the forum to parse.
  
With theses parameters, the program determines the post container characteristics (the container tag name, the value of the container attribute class)

After the program crawls the forum and gets all URLs, for each URL, it parse the page and extract posts if it finds them.
