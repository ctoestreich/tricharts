package com.tgid.tri.data.parsing

class TriathlonResultsParsingService {

   String parseResults(String url){
       def data = new URL(url).text

       data
   }
}
