# avgn-jt-ingestor

## Description
Simple and basic utility to ingest data from large input text files with a custom format.

## General considerations
This utility takes an input file (pointed via CLI arguments) and process it to extract data that matches the given restrictions as CLI arguments. The input file must follow some very specific formatting restrictions in order to be "searchable":
 - A line must start either with F or D
 - If the line starts with F it can only be F1 or F2 to specify the format of the following data lines. Many F lines can be given but only the last one will be considered when a data line is found
 - If the line starts with a D then it is treated as a "data line" and should contain a row of data (name, city, id) but the separator used will depend on the active "format"

## Usage example
java -jar application.jar input.txt CITY CARTAGENA

## Input file example
```
F1 
D Erica Burns,BARCELONA,93654902Y 
D Lucy Mcgee,LONDON,51011156P 
D Mitchell Newton,SAN FRANCISCO,25384390A 
D Margarita Richards,LAS VEGAS,09877359D 
D Rhonda Hopkins,SAN FRANCISCO,54315871Z 
D Antonia Harper,LAS VEGAS,27466628M 
D Hilda Caldwell,LONDON,61682270L 
D Alexander Arnold,SAN FRANCISCO,21743514G 
D Cheryl Hawkins,LAS VEGAS,76878166E 
D Renee Anderson,BARCELONA,44340637H 
F2 
D Mitchell Newton ; LAS VEGAS ; 25384390-A 
D Margarita Richards ; NEW YORK ; 09877359-D 
D Rhonda Hopkins ; BARCELONA ; 54315871-Z 
D Taylor Matthews ; LISBOA ; 58202263-G 
D Shelley Payne ; MADRID ; 54808168-L 
D Johnathan Cooper ; PARIS ; 10863096-N
```

## TODOs
 - Use Threads and semaphores to implement the searching engine to provide better performance and inform the user about progress in processing.
 - Use CLI flags to control the behavior and default settings of the program.
 - Profile the program an heavy loads to find bottlenecks
 - Document the code extensively.
 - Add more tests
