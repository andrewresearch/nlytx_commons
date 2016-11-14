package io.nlytx.commons.tfidf

import io.nlytx.commons.UnitSpec

/**
  * Created by andrew@andrewresearch.net on 14/11/16.
  */


class TfIdfSpec extends UnitSpec {

  val text = "This is a string. And here is another string ending with a newline.\n This is still another one but it doesn't end with a newline!"
  val rtf = TfIdf.rawTermFrequency(text)
  val wtf = TfIdf.weightedTermFrequency(text)

  val texts = List("Document one is document","This is document two is three","three three document","and this is document three four")
  val dtc = texts.map(TfIdf.rawTermFrequency)
  val tdc = TfIdf.termDocCounts(dtc)
  val idf = TfIdf.inverseDocFrequency(tdc,4)

  //val tfidf = texts.map(t => TfIdf.tfIdf(TfIdf.weightedTermFrequency(t),idf))
  val tfidf = TfIdf.calculate(texts)
  val ranked = TfIdf.rankedTerms(texts)

  "rawTermFrequency" should "result in an accurate count of words in a string" in {
    assert(rtf.size==17)
    assert(rtf.getOrElse("this",0L)==2L)
    assert(rtf.getOrElse("is",0L)==3L)
    assert(rtf.getOrElse("a",0L)==3L)
    assert(rtf.getOrElse("string",0L)==2L)
    assert(rtf.getOrElse("and",0L)==1L)
    assert(rtf.getOrElse("here",0L)==1L)
    assert(rtf.getOrElse("another",0L)==2L)
    assert(rtf.getOrElse("ending",0L)==1L)
    assert(rtf.getOrElse("with",0L)==2L)
    assert(rtf.getOrElse("newline",0L)==2L)
    assert(rtf.getOrElse("still",0L)==1L)
    assert(rtf.getOrElse("one",0L)==1L)
    assert(rtf.getOrElse("but",0L)==1L)
    assert(rtf.getOrElse("it",0L)==1L)
    assert(rtf.getOrElse("doesn",0L)==1L)
    assert(rtf.getOrElse("t",0L)==1L)
    assert(rtf.getOrElse("end",0L)==1L)
  }

//  "maxTermFrequency" should "return the maximum count of all rawTermFrequencies" in {
//    assert(max==3L)
//  }
//
//  "weightedTerm" should "return an accurate weighted rating based on a count and a max" in {
//    assert(TfIdf.weightedTerm("test",1,4)._2==0.625)
//  }

  "weightedTermFrequency" should "produce correct weightings" in {
    wtf.size should equal (17)
    wtf.getOrElse("this",0.0) should (be > 0.8333 and be < 0.8334)
    wtf.getOrElse("is",0.0) should equal (1.0)
    wtf.getOrElse("a",0.0) should equal (1.0)
    wtf.getOrElse("string",0.0) should (be > 0.8333 and be < 0.8334)
    wtf.getOrElse("and",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("here",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("another",0.0) should (be > 0.8333 and be < 0.8334)
    wtf.getOrElse("ending",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("with",0.0) should (be > 0.8333 and be < 0.8334)
    wtf.getOrElse("newline",0.0) should (be > 0.8333 and be < 0.8334)
    wtf.getOrElse("still",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("one",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("but",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("it",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("doesn",0.0) should (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("t",0.0) should  (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("end",0.0) should  (be > 0.6666 and be < 0.6667)
    wtf.getOrElse("notAtem",0.0) should equal (0.0)
  }

  "termDocumentCounts" should "produce correct document counts" in {
    tdc.size should equal (8)
    tdc.getOrElse("document",0) should equal (5)
    tdc.getOrElse("one",0) should equal (1)
    tdc.getOrElse("this",0) should equal (2)
    tdc.getOrElse("is",0) should equal (4)
    tdc.getOrElse("two",0) should equal (1)
    tdc.getOrElse("three",0) should equal (4)
    tdc.getOrElse("and",0) should equal (1)
    tdc.getOrElse("four",0) should equal (1)
  }

  "inverseDocFrequency" should "produce correct idf values" in {
    idf.getOrElse("document",0.0) should equal (0.0)
    idf.getOrElse("one",0) should equal (1.6094379124341003)
    idf.getOrElse("this",0) should equal (1.0986122886681096)
    idf.getOrElse("is",0) should equal (0.6931471805599453)
    idf.getOrElse("two",0) should equal (1.6094379124341003)
    idf.getOrElse("three",0) should equal (0.6931471805599453)
    idf.getOrElse("and",0) should equal (1.6094379124341003)
    idf.getOrElse("four",0) should equal (1.6094379124341003)
  }

  // "Document one is document"
  "document 1 tfIdf" should "produce correct tfidf values" in {
    tfidf(0).getOrElse("document",0.0) should equal (0.0)
    tfidf(0).getOrElse("one",0) should equal (1.2070784343255752)
    tfidf(0).getOrElse("this",0) should equal (0.0)
    tfidf(0).getOrElse("is",0) should equal (0.5198603854199589)
    tfidf(0).getOrElse("two",0) should equal (0.0)
    tfidf(0).getOrElse("three",0) should equal (0.0)
    tfidf(0).getOrElse("and",0) should equal (0.0)
    tfidf(0).getOrElse("four",0) should equal (0.0)
  }

  // "This is document two is three"
  "document 2 tfIdf" should "produce correct tfidf values" in {
    tfidf(1).getOrElse("document",0.0) should equal (0.0)
    tfidf(1).getOrElse("one",0) should equal (0.0)
    tfidf(1).getOrElse("this",0) should equal (0.8239592165010822)
    tfidf(1).getOrElse("is",0) should equal (0.6931471805599453)
    tfidf(1).getOrElse("two",0) should equal (1.2070784343255752)
    tfidf(1).getOrElse("three",0) should equal (0.5198603854199589)
    tfidf(1).getOrElse("and",0) should equal (0.0)
    tfidf(1).getOrElse("four",0) should equal (0.0)
  }

  // "three three document"
  "document 3 tfIdf" should "produce correct tfidf values" in {
    tfidf(2).getOrElse("document",0.0) should equal (0.0)
    tfidf(2).getOrElse("one",0) should equal (0.0)
    tfidf(2).getOrElse("this",0) should equal (0.0)
    tfidf(2).getOrElse("is",0) should equal (0.0)
    tfidf(2).getOrElse("two",0) should equal (0.0)
    tfidf(2).getOrElse("three",0) should equal (0.6931471805599453)
    tfidf(2).getOrElse("and",0) should equal (0.0)
    tfidf(2).getOrElse("four",0) should equal (0.0)
  }

  // "and this is document three four"
  "document 4 tfIdf" should "produce correct tfidf values" in {
    tfidf(3).getOrElse("document",0.0) should equal (0.0)
    tfidf(3).getOrElse("one",0) should equal (0.0)
    tfidf(3).getOrElse("this",0) should equal (1.0986122886681096)
    tfidf(3).getOrElse("is",0) should equal (0.6931471805599453)
    tfidf(3).getOrElse("two",0) should equal (0.0)
    tfidf(3).getOrElse("three",0) should equal (0.6931471805599453)
    tfidf(3).getOrElse("and",0) should equal (1.6094379124341003)
    tfidf(3).getOrElse("four",0) should equal (1.6094379124341003)
  }

  "rankedTerms" should "produce accurate rankings for each document" in {
    ranked(0) should equal (List("one","is"))
    ranked(1) should equal (List("two", "this", "is", "three"))
    ranked(2) should equal (List("three"))
    ranked(3) should equal (List("and", "four", "this", "three", "is"))
  }

}
