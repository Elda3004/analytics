<!-- ABOUT THE PROJECT -->
## About The Project

Spring Boot Proof of Concept project to find mentioned companies in a given list of news articles


### Built With

* [![SpringBoot 3]
* [![Java 21]
* [![Spring Webflux]
* * [![Spring Shell]
* * [![Sax(Simple API xml)]

<!-- GETTING STARTED -->
## Getting Started

Download the as a zip folder or pull from Github link: https://github.com/Elda3004/analytics.git 
Install all the dependencies and start the project.

<!-- USAGE EXAMPLES -->
## Usage

<!-- FUNCTIONALITIES -->
1. Read a large csv file from resources folder, parallel process the companies list and puts in the in-memory cache
2. Read in parallel multiple xml files from resources folder and processes the content of each article then puts the data into in-memory cache
3. Filter in parallel the companies and articles and find articles where the companies are mentioned
4. For fast parsing and reading the xml files I have used SAX reader which is thread safe and uses low memory for reading files

<!-- After Building the Project -->
1. Execute this shell command to read xml files and put into cache
  ``read-articles``

2. Execute this shell command to read csv file and put into cache
   ``read-companies``

3. Execute this shell command to find which company is mentioned in certain article content
   ``search-articles``

The last command will print into the console an object of ```CompanyArticleRecord(companyName, articleContent)```

<!-- Decisions -->

Given the fact I had to read and process a list of more than 18.000 companies and 16.000 news articles I decided to use 
Spring Webflux asynchronous non-blocking model to process these data.

To archive the reading of multiple xml files and parse the content of each article I have used parallel processing with Spring Webflux

<!-- Improvements -->

1. Improve error handling when Async task executor rejects a task in parallel processing
2. Create unit test to address negative use cases when an error is thrown
3. Create tests for commands
4. Improve the efficiency of the filter algorithm for retrieving the companies found in news faster
