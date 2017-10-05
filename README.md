# JAVA---Binary-File-Processing

Purpose of this project is getting familiarized with the concept of search in disk algorithm efficiency. In particular, a binary file is created which contains N integers in binary form. This file is divided in disk pages of 512bytes. The file is initialized with N disk pages. The file is filled concequtive for elements 1 to N. Each element is placed in a specific disk page depending on a function H(key) (where key is the element). The search is implemented by the input of a random number using two different methods.
 1st)
 The algorithm calculates, based on the H(key) function, in which disk page the element is saved. The page is successivly scanned for the element the algorithm seeks. If this page doesnt contain the element, this process is repeated on the overflow pages of this page.
 2nd)
 The algorithm searches the file from the beginning to end , element by element, and finds the desired element
 Finally , efficiency data are provided from this program, comparing the above two methods.
