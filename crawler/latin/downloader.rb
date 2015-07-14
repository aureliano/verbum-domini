`wget http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_jp-ii_apost_const_lt.html -O constitutio_apostolica.html`
`wget http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_praefatio_lt.html -O praefation_lectorem.html`
`wget http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_praenotanda_lt.html -O praenotanda.html`

bible = {
  :vt => %w(
    genesis exodus leviticus numeri deuteronomii iosue iudicum ruth i-samuelis ii-samuelis i-regum ii-regum i-paralipomenon
    ii-paralipomenon esdrae nehemiae thobis iudith esther iob psalmorum proverbiorum ecclesiastes canticum-canticorum
    sapientiae ecclesiasticus isaiae ieremiae lamentationes baruch ezechielis danielis osee ioel amos abdiae ionae michaeae
    nahum habacuc sophoniae aggaei zachariae malachiae i-maccabaeorum ii-maccabaeorum
  ),

  :nt => %w(
    evang-matthaeum evang-marcum evang-lucam evang-ioannem actus-apostolorum epist-romanos epist-i-corinthios
    epist-ii-corinthios epist-galatas epist-ephesios epist-philippenses epist-colossenses epist-i-thessalonicenses
    epist-ii-thessalonicenses epist-i-timotheum epist-ii-timotheum epist-titum epist-philemonem epist-hebraeos epist-iacobi
    epist-i-petri epist-ii-petri epist-i-ioannis epist-ii-ioannis epist-iii-ioannis epist-iudae apocalypsis-ioannis
  )
}

bible.keys.each do |testament|
  dir = testament.to_s.upcase
  `mkdir #{dir}` unless File.exist? dir
  
  bible[testament].each do |book|
    url = "http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_#{testament}_#{book}_lt.html"
    `wget #{url} -O #{dir}/#{book}.html`
  end
end

dir = 'Appendix'
`mkdir #{dir}` unless File.exist? dir

`wget http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_appendix_decretum-can-script_lt.html -O #{dir}/decretum-can-script.html`
`wget http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_appendix_decr-editione-usu_lt.html -O #{dir}/decr-editione-usu.html`
`wget http://www.vatican.va/archive/bible/nova_vulgata/documents/nova-vulgata_appendix_praefatio-lectorem_lt.html -O #{dir}/praefatio-lectorem.html`

# http://www.vatican.va/archive/bible/index_po.htm