package io.nlytx.commons.tfidf

/**
  * Created by andrew@andrewresearch.net on 14/11/16.
  */
object TfIdf {

  def rankedTerms(docs:List[String]):List[List[String]] = {
    val tfIdf = calculate(docs)
    tfIdf.map { d =>
      d.filterNot(_._2 == 0.0).toSeq.sortBy(_._2).reverse.map(_._1).toList
    }
  }

  def calculate(docs:List[String]):List[Map[String,Double]] = {
    val dtc:List[Map[String,Long]] = docs.map(rawTermFrequency)
    val wtc:List[Map[String,Double]] = docs.map(weightedTermFrequency)
    val tdc:Map[String,Long] = termDocCounts(dtc)
    val idf:Map[String,Double] = inverseDocFrequency(tdc,docs.length)
    wtc.map(d => tfIdf(d,idf))
  }

  def rawTermFrequency(doc:String):Map[String,Long] = {
    splitWords(doc).foldLeft(Map.empty[String, Long]) {
      (count, word) => count + (word -> (count.getOrElse(word, 0L) + 1L))
    }
  }

  def weightedTermFrequency(doc:String):Map[String,Double] = {
    val rtf:Map[String,Long] = rawTermFrequency(doc)
    val max = maxTermFrequency(rtf)
    rtf.map(f => weightedTerm(f._1,f._2,max))
  }

  private def splitWords(text:String):List[String] = text.split("\\W+").toList.map(_.toLowerCase)
  private def maxTermFrequency(termCounts:Map[String,Long]):Long = termCounts.maxBy(_._2)._2
  private def weightedTerm(term:String,count:Long,max:Long):(String,Double) = term -> weightedCount(count,max)
  private def weightedCount(count:Long,max:Long):Double = (0.5 + (0.5 * (count.toDouble / max)))


  def termDocCounts(docTermCounts:List[Map[String,Long]]):Map[String,Long] = {
    docTermCounts.flatten.groupBy(_._1).map(kv => kv._1 -> kv._2.map(_._2).sum)
  }

  def inverseDocFrequency(docCountsPerTerm:Map[String,Long],docsInCollection:Long):Map[String,Double] = {
    docCountsPerTerm.map( dc => dc._1 -> math.log1p(docsInCollection / dc._2))
  }

  def tfIdf(tf:Map[String,Double],idf:Map[String,Double]):Map[String,Double] =  tf.map( t => t._1 -> (idf.getOrElse(t._1,0.0) * t._2.toDouble))


}
