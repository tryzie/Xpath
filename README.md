# Xpath
This shows how to use xpaths, using regEx and  validate xml 



signatories_model_info.xml

 Without using Xpaths: 
a. Get all elements/atributes which have field_type API_BASED. List them by their 
‘tag_name’ atribute
b. Count all the elements which are field_type TABLE_BASED. 
c. Get all the elements which are to be checked for duplicates and the associated fields 
d. Remove the XML elements RESTRICTED_ACCESS_NATIONALITIES_MATCH_TYPE, 
MAX_RESTRICTED_ACCESS_NATIONALITIES, RESTRICTED_ACCESS_NATIONALITIES 
e. Update all elements where use=’MANDATORY’, to be OPTIONAL 
 Use Xpaths to achieve the above as well.


XML name: pac008_sample.xml

 Create an XSD which validates the following XML. 
 Use Xpaths to read the values of all the elements in the XML 
 Inside element OrgnlTxRef, create another element: 
 <CdtrAcct> 
 <Id> 
 <Othr> 
 <Id/> 
 </Othr> 
 </Id> 
 </CdtrAcct>
