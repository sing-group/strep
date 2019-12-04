-- Use this file to include the sentences to init the database (default VALUES)
-- INSERT here the default users, roles, etc.

--Default VALUES for the table datasetType
INSERT IGNORE INTO dataset_types(id, description, name) VALUES(0, "Datasets uploaded to the platform by users with upload dataset permissions","systemdataset");
INSERT IGNORE INTO dataset_types(id, description, name) VALUES(1, "Datasets created by users mixing system datasets","userdataset");
 
--Admin password 12admin34
INSERT IGNORE INTO user(username, confirmed_account, email, hash, name, encrypted_password, photo, surname, permission_id) VALUES ('admin',b'1','onlinepreprocessor@gmail.com','system_default','System Administrator','$2a$10$3Gfa1K4Te7xeE4s8cvOhnecyN7v.iai4GXkhlrX1JXokt1PLHrKYi',null,'',4);
INSERT IGNORE INTO permission(id, name, description) VALUES (1,"View", "Users can: view system datasets, view protected datasets and request permissions");
INSERT IGNORE INTO permission(id, name, description) VALUES (2,"CreateCorpus", "Users can: view system datasets, view protected datasets, view and create their own datasets by mixing system datasets and requesting permissions.");
INSERT IGNORE INTO permission(id, name, description) VALUES (3,"Upload", "Users can: view system datasets, view protected datasets, view and create their own datasets by mixing those in the system. They can also upload system datasets and request permissions.");
INSERT IGNORE INTO permission(id, name, description) VALUES (4,"Administer", "All privileges are granted.");
--INSERT IGNORE INTO user_perm(user,perm_id) VALUES ("admin",4);

INSERT IGNORE INTO language(language,description) VALUES("AF","Afrikaans");
INSERT IGNORE INTO language(language,description) VALUES("AN","Aragonese");
INSERT IGNORE INTO language(language,description) VALUES("AR","Arabic");
INSERT IGNORE INTO language(language,description) VALUES("AST","Asturian");
INSERT IGNORE INTO language(language,description) VALUES("BE","Belarusian");
INSERT IGNORE INTO language(language,description) VALUES("BR","Breton");
INSERT IGNORE INTO language(language,description) VALUES("CA","Catalan");
INSERT IGNORE INTO language(language,description) VALUES("BG","Bulgarian");
INSERT IGNORE INTO language(language,description) VALUES("BN","Bengali");
INSERT IGNORE INTO language(language,description) VALUES("CS","Czech");
INSERT IGNORE INTO language(language,description) VALUES("CY","Welsh");
INSERT IGNORE INTO language(language,description) VALUES("DA","Danish");
INSERT IGNORE INTO language(language,description) VALUES("DE","German");
INSERT IGNORE INTO language(language,description) VALUES("EL","Greek");
INSERT IGNORE INTO language(language,description) VALUES("EN","English");
INSERT IGNORE INTO language(language,description) VALUES("ES","Spanish");
INSERT IGNORE INTO language(language,description) VALUES("ET","Estonian");
INSERT IGNORE INTO language(language,description) VALUES("EU","Basque");
INSERT IGNORE INTO language(language,description) VALUES("FA","Persian");
INSERT IGNORE INTO language(language,description) VALUES("FI","Finnish");
INSERT IGNORE INTO language(language,description) VALUES("FR","French");
INSERT IGNORE INTO language(language,description) VALUES("GA","Irish");
INSERT IGNORE INTO language(language,description) VALUES("GL","Galician");
INSERT IGNORE INTO language(language,description) VALUES("GU","Gujarati");
INSERT IGNORE INTO language(language,description) VALUES("HE","Hebrew");
INSERT IGNORE INTO language(language,description) VALUES("HI","Hindi");
INSERT IGNORE INTO language(language,description) VALUES("HR","Croatian");
INSERT IGNORE INTO language(language,description) VALUES("HT","Haitian");
INSERT IGNORE INTO language(language,description) VALUES("HU","Hungarian");
INSERT IGNORE INTO language(language,description) VALUES("ID","Indonesian");
INSERT IGNORE INTO language(language,description) VALUES("IS","Icelandic");
INSERT IGNORE INTO language(language,description) VALUES("IT","Italian");
INSERT IGNORE INTO language(language,description) VALUES("JA","Japanese");
INSERT IGNORE INTO language(language,description) VALUES("KM","Khmer");
INSERT IGNORE INTO language(language,description) VALUES("KN","Kannada");
INSERT IGNORE INTO language(language,description) VALUES("KO","Korean");
INSERT IGNORE INTO language(language,description) VALUES("LT","Lithuanian");
INSERT IGNORE INTO language(language,description) VALUES("LV","Latvian");
INSERT IGNORE INTO language(language,description) VALUES("MK","Macedonian");
INSERT IGNORE INTO language(language,description) VALUES("ML","Malayalam");
INSERT IGNORE INTO language(language,description) VALUES("MR","Marathi");
INSERT IGNORE INTO language(language,description) VALUES("MS","Malay");
INSERT IGNORE INTO language(language,description) VALUES("MT","Maltese");
INSERT IGNORE INTO language(language,description) VALUES("NE","Nepali");
INSERT IGNORE INTO language(language,description) VALUES("NL","Dutch");
INSERT IGNORE INTO language(language,description) VALUES("NO","Norwegian");
INSERT IGNORE INTO language(language,description) VALUES("OC","Occitan");
INSERT IGNORE INTO language(language,description) VALUES("PA","Punjabi");
INSERT IGNORE INTO language(language,description) VALUES("PL","Polish");
INSERT IGNORE INTO language(language,description) VALUES("PT","Portuguese");
INSERT IGNORE INTO language(language,description) VALUES("RO","Romanian");
INSERT IGNORE INTO language(language,description) VALUES("RU","Russian");
INSERT IGNORE INTO language(language,description) VALUES("SK","Slovak");
INSERT IGNORE INTO language(language,description) VALUES("SL","Slovene");
INSERT IGNORE INTO language(language,description) VALUES("SO","Somali");
INSERT IGNORE INTO language(language,description) VALUES("SQ","Albanian");
INSERT IGNORE INTO language(language,description) VALUES("SR","Serbian");
INSERT IGNORE INTO language(language,description) VALUES("SV","Swedish");
INSERT IGNORE INTO language(language,description) VALUES("SW","Swahili");
INSERT IGNORE INTO language(language,description) VALUES("TA","Tamil");
INSERT IGNORE INTO language(language,description) VALUES("TE","Telugu");
INSERT IGNORE INTO language(language,description) VALUES("TH","Thai");
INSERT IGNORE INTO language(language,description) VALUES("TL","Tagalog");
INSERT IGNORE INTO language(language,description) VALUES("TR","Turkish");
INSERT IGNORE INTO language(language,description) VALUES("UK","Ukrainian");
INSERT IGNORE INTO language(language,description) VALUES("UR","Urdu");
INSERT IGNORE INTO language(language,description) VALUES("VI","Vietnamese");
INSERT IGNORE INTO language(language,description) VALUES("WA","Walloon");
INSERT IGNORE INTO language(language,description) VALUES("YI","Yiddish");
INSERT IGNORE INTO language(language,description) VALUES("ZH-CN","Simplified Chinese");
INSERT IGNORE INTO language(language,description) VALUES("ZH-TW","Traditional Chinese");

INSERT IGNORE INTO datatype(dataType) VALUES(".eml");
INSERT IGNORE INTO datatype(dataType) VALUES(".tsms");
INSERT IGNORE INTO datatype(dataType) VALUES(".twtid");
INSERT IGNORE INTO datatype(dataType) VALUES(".warc");
INSERT IGNORE INTO datatype(dataType) VALUES(".ytbid");

INSERT IGNORE INTO license(name, description, url) VALUES("Public domain", '
Public Domain Mark

No Known Copyright
PDM

Our Public Domain Mark enables works that are no longer restricted by copyright to be marked as such in a standard and simple way, making them easily discoverable and available to others. Many cultural heritage institutions including museums, libraries and other curators are knowledgeable about the copyright status of paintings, books and manuscripts, photographs and other works in their collections, many of which are old and no longer under copyright.  The Public Domain Mark operates as a tag or a label, allowing institutions like those as well as others with such knowledge to communicate that a work is no longer restricted by copyright and can be freely used by others.  The mark can also be an important source of information, allowing others to verify a work’s copyright status and learn more about the work.
Recommended Uses of the Public Domain Mark

The Public Domain Mark is recommended for works that are free of known copyright around the world. These will typically be very old works.  It is not recommended for use with works that are in the public domain in some jurisdictions if they also known to be restricted by copyright in others.

A work may have limited or "hybrid" public domain status for several reasons.  Some jurisdictions have unusually long copyright terms, which may mean that a work free from copyright restrictions most everywhere in the world could still be protected by the copyright laws of that particular country.  Sometimes a work is no longer restricted by copyright in a jurisdiction because the author or owner failed to comply with local formalities such as renewal, where those formalities apply.  It could also be the case for works that are deemed not protected by copyright by operation of law in a particular jurisdiction, but that are afforded protection under the copyright laws of other jurisdictions.

CC does not recommend the Public Domain Mark for works with limited, hybrid public domain status at this time, though we will be exploring means for doing so in 2014.', "https://creativecommons.org/share-your-work/public-domain/pdm/");

INSERT IGNORE INTO license(name, description, url) VALUES("CC-0", 'Statement of Purpose

The laws of most jurisdictions throughout the world automatically confer exclusive Copyright and Related Rights (defined below) upon the creator and subsequent owner(s) (each and all, an "owner") of an original work of authorship and/or a database (each, a "Work").

Certain owners wish to permanently relinquish those rights to a Work for the purpose of contributing to a commons of creative, cultural and scientific works ("Commons") that the public can reliably and without fear of later claims of infringement build upon, modify, incorporate in other works, reuse and redistribute as freely as possible in any form whatsoever and for any purposes, including without limitation commercial purposes. These owners may contribute to the Commons to promote the ideal of a free culture and the further production of creative, cultural and scientific works, or to gain reputation or greater distribution for their Work in part through the use and efforts of others.

For these and/or other purposes and motivations, and without any expectation of additional consideration or compensation, the person associating CC0 with a Work (the "Affirmer"), to the extent that he or she is an owner of Copyright and Related Rights in the Work, voluntarily elects to apply CC0 to the Work and publicly distribute the Work under its terms, with knowledge of his or her Copyright and Related Rights in the Work and the meaning and intended legal effect of CC0 on those rights.

1. Copyright and Related Rights. A Work made available under CC0 may be protected by copyright and related or neighboring rights ("Copyright and Related Rights"). Copyright and Related Rights include, but are not limited to, the following:

    the right to reproduce, adapt, distribute, perform, display, communicate, and translate a Work;
    moral rights retained by the original author(s) and/or performer(s);
    publicity and privacy rights pertaining to a person`s image or likeness depicted in a Work;
    rights protecting against unfair competition in regards to a Work, subject to the limitations in paragraph 4(a), below;
    rights protecting the extraction, dissemination, use and reuse of data in a Work;
    database rights (such as those arising under Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, and under any national implementation thereof, including any amended or successor version of such directive); and
    other similar, equivalent or corresponding rights throughout the world based on applicable law or treaty, and any national implementations thereof.

2. Waiver. To the greatest extent permitted by, but not in contravention of, applicable law, Affirmer hereby overtly, fully, permanently, irrevocably and unconditionally waives, abandons, and surrenders all of Affirmer`s Copyright and Related Rights and associated claims and causes of action, whether now known or unknown (including existing as well as future claims and causes of action), in the Work (i) in all territories worldwide, (ii) for the maximum duration provided by applicable law or treaty (including future time extensions), (iii) in any current or future medium and for any number of copies, and (iv) for any purpose whatsoever, including without limitation commercial, advertising or promotional purposes (the "Waiver"). Affirmer makes the Waiver for the benefit of each member of the public at large and to the detriment of Affirmer`s heirs and successors, fully intending that such Waiver shall not be subject to revocation, rescission, cancellation, termination, or any other legal or equitable action to disrupt the quiet enjoyment of the Work by the public as contemplated by Affirmer`s express Statement of Purpose.

3. Public License Fallback. Should any part of the Waiver for any reason be judged legally invalid or ineffective under applicable law, then the Waiver shall be preserved to the maximum extent permitted taking INTO account Affirmer`s express Statement of Purpose. In addition, to the extent the Waiver is so judged Affirmer hereby grants to each affected person a royalty-free, non transferable, non sublicensable, non exclusive, irrevocable and unconditional license to exercise Affirmer`s Copyright and Related Rights in the Work (i) in all territories worldwide, (ii) for the maximum duration provided by applicable law or treaty (including future time extensions), (iii) in any current or future medium and for any number of copies, and (iv) for any purpose whatsoever, including without limitation commercial, advertising or promotional purposes (the "License"). The License shall be deemed effective as of the date CC0 was applied by Affirmer to the Work. Should any part of the License for any reason be judged legally invalid or ineffective under applicable law, such partial invalidity or ineffectiveness shall not invalidate the remainder of the License, and in such case Affirmer hereby affirms that he or she will not (i) exercise any of his or her remaining Copyright and Related Rights in the Work or (ii) assert any associated claims and causes of action with respect to the Work, in either case contrary to Affirmer`s express Statement of Purpose.

4. Limitations and Disclaimers.

    No trademark or patent rights held by Affirmer are waived, abandoned, surrendered, licensed or otherwise affected by this document.
    Affirmer offers the Work as-is and makes no representations or warranties of any kind concerning the Work, express, implied, statutory or otherwise, including without limitation warranties of title, merchantability, fitness for a particular purpose, non infringement, or the absence of latent or other defects, accuracy, or the present or absence of errors, whether or not discoverable, all to the greatest extent permissible under applicable law.
    Affirmer disclaims responsibility for clearing rights of other persons that may apply to the Work or any use thereof, including without limitation any person`s Copyright and Related Rights in the Work. Further, Affirmer disclaims responsibility for obtaining any necessary consents, permissions or other rights required for any use of the Work.
    Affirmer understands and acknowledges that Creative Commons is not a party to this document and has no duty or obligation with respect to this CC0 or use of the Work.',"https://creativecommons.org/publicdomain/zero/1.0/legalcode" 
);

INSERT IGNORE INTO license(name, description, url) VALUES(
'PDDL', 'ODC Public Domain Dedication and Licence (PDDL)
Disclaimer

PLEASE READ:

Open Data Commons is not a law firm and does not provide legal services of any kind.

Open Data Commons has no formal relationship with you. Your receipt of this document does not create any kind of agent-client relationship. Please seek the advice of a suitably qualified legal professional licensed to practice in your jurisdiction before using this document.

No warranties and disclaimer of any damages.

This information is provided ‘as is‘, and this site makes no warranties on the information provided. Any damages resulting from its use are disclaimed.

READ the full disclaimer here.
The License

Preamble
The Open Data Commons - Public Domain Dedication & Licence is a document intended to allow you to freely share, modify, and use this work for any purpose and without any restrictions. This licence is intended for use on databases or their contents ("data"), either together or individually.

Many databases are covered by copyright. Some jurisdictions, mainly in Europe, have specific special rights that cover databases called the "sui generis" database right. Both of these sets of rights, as well as other legal rights used to protect databases and data, can create uncertainty or practical difficulty for those wishing to share databases and their underlying data but retain a limited amount of rights under a "some rights reserved" approach to licensing as outlined in the Science Commons Protocol for Implementing Open Access Data. As a result, this waiver and licence tries to the fullest extent possible to eliminate or fully license any rights that cover this database and data. Any Community Norms or similar statements of use of the database or data do not form a part of this document, and do not act as a contract for access or other terms of use for the database or data.

The position of the recipient of the work

Because this document places the database and its contents in or as close as possible within the public domain, there are no restrictions or requirements placed on the recipient by this document. Recipients may use this work commercially, use technical protection measures, combine this data or database with other databases or data, and share their changes and additions or keep them secret. It is not a requirement that recipients provide further users with a copy of this licence or attribute the original creator of the data or database as a source. The goal is to eliminate restrictions held by the original creator of the data and database on the use of it by others.

The position of the dedicator of the work

Copyright law, as with most other law under the banner of "intellectual property", is inherently national law. This means that there exists several differences in how copyright and other IP rights can be relinquished, waived or licensed in the many legal jurisdictions of the world. This is despite much harmonisation of minimum levels of protection. The internet and other communication technologies span these many disparate legal jurisdictions and thus pose special difficulties for a document relinquishing and waiving intellectual property rights, including copyright and database rights, for use by the global community. Because of this feature of intellectual property law, this document first relinquishes the rights and waives the relevant rights and claims. It then goes on to license these same rights for jurisdictions or areas of law that may make it difficult to relinquish or waive rights or claims.

The purpose of this document is to enable rightsholders to place their work INTO the public domain. Unlike licences for free and open source software, free cultural works, or open content licences, rightsholders will not be able to "dual license" their work by releasing the same work under different licences. This is because they have allowed anyone to use the work in whatever way they choose. Rightsholders therefore can’t re-license it under copyright or database rights on different terms because they have nothing left to license. Doing so creates truly accessible data to build rich applications and advance the progress of science and the arts.

This document can cover either or both of the database and its contents (the data). Because databases can have a wide variety of content - not just factual data - rightsholders should use the Open Data Commons - Public Domain Dedication & Licence for an entire database and its contents only if everything can be placed under the terms of this document. Because even factual data can sometimes have intellectual property rights, rightsholders should use this licence to cover both the database and its factual data when making material available under this document; even if it is likely that the data would not be covered by copyright or database rights.

Rightsholders can also use this document to cover any copyright or database rights claims over only a database, and leave the contents to be covered by other licences or documents. They can do this because this document refers to the "Work", which can be either - or both - the database and its contents. As a result, rightsholders need to clearly state what they are dedicating under this document when they dedicate it.

Just like any licence or other document dealing with intellectual property, rightsholders should be aware that one can only license what one owns. Please ensure that the rights have been cleared to make this material available under this document.

This document permanently and irrevocably makes the Work available to the public for any use of any kind, and it should not be used unless the rightsholder is prepared for this to happen.

Part I: Introduction

The Rightsholder (the Person holding rights or claims over the Work) agrees as follows:

1.0 Definitions of Capitalised Words

"Copyright" - Includes rights under copyright and under neighbouring rights and similarly related sets of rights under the law of the relevant jurisdiction under Section 6.4.

"Data" - The contents of the Database, which includes the information, independent works, or other material collected INTO the Database offered under the terms of this Document.

"Database" - A collection of Data arranged in a systematic or methodical way and individually accessible by electronic or other means offered under the terms of this Document.

"Database Right" - Means rights over Data resulting from the Chapter III ("sui generis") rights in the Database Directive (Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases) and any future updates as well as any similar rights available in the relevant jurisdiction under Section 6.4.

"Document" - means this relinquishment and waiver of rights and claims and back up licence agreement.

"Person" - Means a natural or legal person or a body of persons corporate or incorporate.

"Use" - As a verb, means doing any act that is restricted by Copyright or Database Rights whether in the original medium or any other; and includes modifying the Work as may be technically necessary to use it in a different mode or format. This includes the right to sublicense the Work.

"Work" - Means either or both of the Database and Data offered under the terms of this Document.

"You" - the Person acquiring rights under the licence elements of this Document.

Words in the singular include the plural and vice versa.

2.0 What this document covers

2.1. Legal effect of this Document. This Document is:

a. A dedication to the public domain and waiver of Copyright and Database Rights over the Work; and

b. A licence of Copyright and Database Rights over the Work in jurisdictions that do not allow for relinquishment or waiver.

2.2. Legal rights covered.

a. Copyright. Any copyright or neighbouring rights in the Work. Copyright law varies between jurisdictions, but is likely to cover: the Database model or schema, which is the structure, arrangement, and organisation of the Database, and can also include the Database tables and table indexes; the data entry and output sheets; and the Field names of Data stored in the Database. Copyright may also cover the Data depending on the jurisdiction and type of Data; and

b. Database Rights. Database Rights only extend to the extraction and re-utilisation of the whole or a substantial part of the Data. Database Rights can apply even when there is no copyright over the Database. Database Rights can also apply when the Data is removed from the Database and is selected and arranged in a way that would not infringe any applicable copyright.

2.2 Rights not covered.

a. This Document does not apply to computer programs used in the making or operation of the Database;

b. This Document does not cover any patents over the Data or the Database. Please see Section 4.2 later in this Document for further details; and

c. This Document does not cover any trade marks associated with the Database. Please see Section 4.3 later in this Document for further details.

Users of this Database are cautioned that they may have to clear other rights or consult other licences.

2.3 Facts are free. The Rightsholder takes the position that factual information is not covered by Copyright. This Document however covers the Work in jurisdictions that may protect the factual information in the Work by Copyright, and to cover any information protected by Copyright that is contained in the Work.

Part II: Dedication to the public domain

3.0 Dedication, waiver, and licence of Copyright and Database Rights

3.1 Dedication of Copyright and Database Rights to the public domain. The Rightsholder by using this Document, dedicates the Work to the public domain for the benefit of the public and relinquishes all rights in Copyright and Database Rights over the Work.

a. The Rightsholder realises that once these rights are relinquished, that the Rightsholder has no further rights in Copyright and Database Rights over the Work, and that the Work is free and open for others to Use.

b. The Rightsholder intends for their relinquishment to cover all present and future rights in the Work under Copyright and Database Rights, whether they are vested or contingent rights, and that this relinquishment of rights covers all their heirs and successors.

The above relinquishment of rights applies worldwide and includes media and formats now known or created in the future.

3.2 Waiver of rights and claims in Copyright and Database Rights when Section 3.1 dedication inapplicable. If the dedication in Section 3.1 does not apply in the relevant jurisdiction under Section 6.4, the Rightsholder waives any rights and claims that the Rightsholder may have or acquire in the future over the Work in:

a. Copyright; and

b. Database Rights.

To the extent possible in the relevant jurisdiction, the above waiver of rights and claims applies worldwide and includes media and formats now known or created in the future. The Rightsholder agrees not to assert the above rights and waives the right to enforce them over the Work.

3.3 Licence of Copyright and Database Rights when Sections 3.1 and 3.2 inapplicable. If the dedication and waiver in Sections 3.1 and 3.2 does not apply in the relevant jurisdiction under Section 6.4, the Rightsholder and You agree as follows:

a. The Licensor grants to You a worldwide, royalty-free, non-exclusive, licence to Use the Work for the duration of any applicable Copyright and Database Rights. These rights explicitly include commercial use, and do not exclude any field of endeavour. To the extent possible in the relevant jurisdiction, these rights may be exercised in all media and formats whether now known or created in the future.

3.4 Moral rights. This section covers moral rights, including the right to be identified as the author of the Work or to object to treatment that would otherwise prejudice the author’s honour and reputation, or any other derogatory treatment:

a. For jurisdictions allowing waiver of moral rights, Licensor waives all moral rights that Licensor may have in the Work to the fullest extent possible by the law of the relevant jurisdiction under Section 6.4;

b. If waiver of moral rights under Section 3.4 a in the relevant jurisdiction is not possible, Licensor agrees not to assert any moral rights over the Work and waives all claims in moral rights to the fullest extent possible by the law of the relevant jurisdiction under Section 6.4; and

c. For jurisdictions not allowing waiver or an agreement not to assert moral rights under Section 3.4 a and b, the author may retain their moral rights over the copyrighted aspects of the Work.

Please note that some jurisdictions do not allow for the waiver of moral rights, and so moral rights may still subsist over the work in some jurisdictions.

4.0 Relationship to other rights

4.1 No other contractual conditions. The Rightsholder makes this Work available to You without any other contractual obligations, either express or implied. Any Community Norms statement associated with the Work is not a contract and does not form part of this Document.

4.2 Relationship to patents. This Document does not grant You a licence for any patents that the Rightsholder may own. Users of this Database are cautioned that they may have to clear other rights or consult other licences.

4.3 Relationship to trade marks. This Document does not grant You a licence for any trade marks that the Rightsholder may own or that the Rightsholder may use to cover the Work. Users of this Database are cautioned that they may have to clear other rights or consult other licences.

Part III: General provisions

5.0 Warranties, disclaimer, and limitation of liability

5.1 The Work is provided by the Rightsholder "as is" and without any warranty of any kind, either express or implied, whether of title, of accuracy or completeness, of the presence of absence of errors, of fitness for purpose, or otherwise. Some jurisdictions do not allow the exclusion of implied warranties, so this exclusion may not apply to You.

5.2 Subject to any liability that may not be excluded or limited by law, the Rightsholder is not liable for, and expressly excludes, all liability for loss or damage however and whenever caused to anyone by any use under this Document, whether by You or by anyone else, and whether caused by any fault on the part of the Rightsholder or not. This exclusion of liability includes, but is not limited to, any special, incidental, consequential, punitive, or exemplary damages. This exclusion applies even if the Rightsholder has been advised of the possibility of such damages.

5.3 If liability may not be excluded by law, it is limited to actual and direct financial loss to the extent it is caused by proved negligence on the part of the Rightsholder.

6.0 General

6.1 If any provision of this Document is held to be invalid or unenforceable, that must not affect the validity or enforceability of the remainder of the terms of this Document.

6.2 This Document is the entire agreement between the parties with respect to the Work covered here. It replaces any earlier understandings, agreements or representations with respect to the Work not specified here.

6.3 This Document does not affect any rights that You or anyone else may independently have under any applicable law to make any use of this Work, including (for jurisdictions where this Document is a licence) fair dealing, fair use, database exceptions, or any other legally recognised limitation or exception to infringement of copyright or other applicable laws.

6.4 This Document takes effect in the relevant jurisdiction in which the Document terms are sought to be enforced. If the rights waived or granted under applicable law in the relevant jurisdiction includes additional rights not waived or granted under this Document, these additional rights are included in this Document in order to meet the intent of this Document.
', 'https://www.opendatacommons.org/licenses/pddl/1.0/');

INSERT IGNORE INTO license(name, description, url) VALUES('CC-BY', 'Creative Commons Attribution 4.0 International Public License

By exercising the Licensed Rights (defined below), You accept and agree to be bound by the terms and conditions of this Creative Commons Attribution 4.0 International Public License (Public License). To the extent this Public License may be interpreted as a contract, You are granted the Licensed Rights in consideration of Your acceptance of these terms and conditions, and the Licensor grants You such rights in consideration of benefits the Licensor receives from making the Licensed Material available under these terms and conditions.

Section 1 - Definitions.

    Adapted Material means material subject to Copyright and Similar Rights that is derived from or based upon the Licensed Material and in which the Licensed Material is translated, altered, arranged, transformed, or otherwise modified in a manner requiring permission under the Copyright and Similar Rights held by the Licensor. For purposes of this Public License, where the Licensed Material is a musical work, performance, or sound recording, Adapted Material is always produced where the Licensed Material is synched in timed relation with a moving image.
    Adapters License means the license You apply to Your Copyright and Similar Rights in Your contributions to Adapted Material in accordance with the terms and conditions of this Public License.
    Copyright and Similar Rights means copyright and/or similar rights closely related to copyright including, without limitation, performance, broadcast, sound recording, and Sui Generis Database Rights, without regard to how the rights are labeled or categorized. For purposes of this Public License, the rights specified in Section 2(b)(1)-(2) are not Copyright and Similar Rights.
    Effective Technological Measures means those measures that, in the absence of proper authority, may not be circumvented under laws fulfilling obligations under Article 11 of the WIPO Copyright Treaty adopted on December 20, 1996, and/or similar international agreements.
    Exceptions and Limitations means fair use, fair dealing, and/or any other exception or limitation to Copyright and Similar Rights that applies to Your use of the Licensed Material.
    Licensed Material means the artistic or literary work, database, or other material to which the Licensor applied this Public License.
    Licensed Rights means the rights granted to You subject to the terms and conditions of this Public License, which are limited to all Copyright and Similar Rights that apply to Your use of the Licensed Material and that the Licensor has authority to license.
    Licensor means the individual(s) or entity(ies) granting rights under this Public License.
    Share means to provide material to the public by any means or process that requires permission under the Licensed Rights, such as reproduction, public display, public performance, distribution, dissemination, communication, or importation, and to make material available to the public including in ways that members of the public may access the material from a place and at a time individually chosen by them.
    Sui Generis Database Rights means rights other than copyright resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other essentially equivalent rights anywhere in the world.
    You means the individual or entity exercising the Licensed Rights under this Public License. Your has a corresponding meaning.

Section 2 - Scope.

    License grant.
        Subject to the terms and conditions of this Public License, the Licensor hereby grants You a worldwide, royalty-free, non-sublicensable, non-exclusive, irrevocable license to exercise the Licensed Rights in the Licensed Material to:
            reproduce and Share the Licensed Material, in whole or in part; and
            produce, reproduce, and Share Adapted Material.
        Exceptions and Limitations. For the avoidance of doubt, where Exceptions and Limitations apply to Your use, this Public License does not apply, and You do not need to comply with its terms and conditions.
        Term. The term of this Public License is specified in Section 6(a).
        Media and formats; technical modifications allowed. The Licensor authorizes You to exercise the Licensed Rights in all media and formats whether now known or hereafter created, and to make technical modifications necessary to do so. The Licensor waives and/or agrees not to assert any right or authority to forbid You from making technical modifications necessary to exercise the Licensed Rights, including technical modifications necessary to circumvent Effective Technological Measures. For purposes of this Public License, simply making modifications authorized by this Section 2(a)(4) never produces Adapted Material.
        Downstream recipients.
            Offer from the Licensor - Licensed Material. Every recipient of the Licensed Material automatically receives an offer from the Licensor to exercise the Licensed Rights under the terms and conditions of this Public License.
            No downstream restrictions. You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, the Licensed Material if doing so restricts exercise of the Licensed Rights by any recipient of the Licensed Material.
        No endorsement. Nothing in this Public License constitutes or may be construed as permission to assert or imply that You are, or that Your use of the Licensed Material is, connected with, or sponsored, endorsed, or granted official status by, the Licensor or others designated to receive attribution as provided in Section 3(a)(1)(A)(i).

    Other rights.
        Moral rights, such as the right of integrity, are not licensed under this Public License, nor are publicity, privacy, and/or other similar personality rights; however, to the extent possible, the Licensor waives and/or agrees not to assert any such rights held by the Licensor to the limited extent necessary to allow You to exercise the Licensed Rights, but not otherwise.
        Patent and trademark rights are not licensed under this Public License.
        To the extent possible, the Licensor waives any right to collect royalties from You for the exercise of the Licensed Rights, whether directly or through a collecting society under any voluntary or waivable statutory or compulsory licensing scheme. In all other cases the Licensor expressly reserves any right to collect such royalties.

Section 3 - License Conditions.

Your exercise of the Licensed Rights is expressly made subject to the following conditions.

    Attribution.

        If You Share the Licensed Material (including in modified form), You must:
            retain the following if it is supplied by the Licensor with the Licensed Material:
                identification of the creator(s) of the Licensed Material and any others designated to receive attribution, in any reasonable manner requested by the Licensor (including by pseudonym if designated);
                a copyright notice;
                a notice that refers to this Public License;
                a notice that refers to the disclaimer of warranties;
                a URI or hyperlink to the Licensed Material to the extent reasonably practicable;
            indicate if You modified the Licensed Material and retain an indication of any previous modifications; and
            indicate the Licensed Material is licensed under this Public License, and include the text of, or the URI or hyperlink to, this Public License.
        You may satisfy the conditions in Section 3(a)(1) in any reasonable manner based on the medium, means, and context in which You Share the Licensed Material. For example, it may be reasonable to satisfy the conditions by providing a URI or hyperlink to a resource that includes the required information.
        If requested by the Licensor, You must remove any of the information required by Section 3(a)(1)(A) to the extent reasonably practicable.
        If You Share Adapted Material You produce, the Adapters License You apply must not prevent recipients of the Adapted Material from complying with this Public License.

Section 4 - Sui Generis Database Rights.

Where the Licensed Rights include Sui Generis Database Rights that apply to Your use of the Licensed Material:

    for the avoidance of doubt, Section 2(a)(1) grants You the right to extract, reuse, reproduce, and Share all or a substantial portion of the contents of the database;
    if You include all or a substantial portion of the database contents in a database in which You have Sui Generis Database Rights, then the database in which You have Sui Generis Database Rights (but not its individual contents) is Adapted Material; and
    You must comply with the conditions in Section 3(a) if You Share all or a substantial portion of the contents of the database.

For the avoidance of doubt, this Section 4 supplements and does not replace Your obligations under this Public License where the Licensed Rights include other Copyright and Similar Rights.

Section 5 - Disclaimer of Warranties and Limitation of Liability.

    Unless otherwise separately undertaken by the Licensor, to the extent possible, the Licensor offers the Licensed Material as-is and as-available, and makes no representations or warranties of any kind concerning the Licensed Material, whether express, implied, statutory, or other. This includes, without limitation, warranties of title, merchantability, fitness for a particular purpose, non-infringement, absence of latent or other defects, accuracy, or the presence or absence of errors, whether or not known or discoverable. Where disclaimers of warranties are not allowed in full or in part, this disclaimer may not apply to You.
    To the extent possible, in no event will the Licensor be liable to You on any legal theory (including, without limitation, negligence) or otherwise for any direct, special, indirect, incidental, consequential, punitive, exemplary, or other losses, costs, expenses, or damages arising out of this Public License or use of the Licensed Material, even if the Licensor has been advised of the possibility of such losses, costs, expenses, or damages. Where a limitation of liability is not allowed in full or in part, this limitation may not apply to You.

    The disclaimer of warranties and limitation of liability provided above shall be interpreted in a manner that, to the extent possible, most closely approximates an absolute disclaimer and waiver of all liability.

Section 6 - Term and Termination.

    This Public License applies for the term of the Copyright and Similar Rights licensed here. However, if You fail to comply with this Public License, then Your rights under this Public License terminate automatically.

    Where Your right to use the Licensed Material has terminated under Section 6(a), it reinstates:
        automatically as of the date the violation is cured, provided it is cured within 30 days of Your discovery of the violation; or
        upon express reinstatement by the Licensor.
    For the avoidance of doubt, this Section 6(b) does not affect any right the Licensor may have to seek remedies for Your violations of this Public License.
    For the avoidance of doubt, the Licensor may also offer the Licensed Material under separate terms or conditions or stop distributing the Licensed Material at any time; however, doing so will not terminate this Public License.
    Sections 1, 5, 6, 7, and 8 survive termination of this Public License.

Section 7 - Other Terms and Conditions.

    The Licensor shall not be bound by any additional or different terms or conditions communicated by You unless expressly agreed.
    Any arrangements, understandings, or agreements regarding the Licensed Material not stated herein are separate from and independent of the terms and conditions of this Public License.

Section 8 - Interpretation.

    For the avoidance of doubt, this Public License does not, and shall not be interpreted to, reduce, limit, restrict, or impose conditions on any use of the Licensed Material that could lawfully be made without permission under this Public License.
    To the extent possible, if any provision of this Public License is deemed unenforceable, it shall be automatically reformed to the minimum extent necessary to make it enforceable. If the provision cannot be reformed, it shall be severed from this Public License without affecting the enforceability of the remaining terms and conditions.
    No term or condition of this Public License will be waived and no failure to comply consented to unless expressly agreed to by the Licensor.
    Nothing in this Public License constitutes or may be interpreted as a limitation upon, or waiver of, any privileges and immunities that apply to the Licensor or You, including from the legal processes of any jurisdiction or authority.
', 'https://creativecommons.org/licenses/by/4.0/legalcode' );

INSERT IGNORE INTO license(name, description, url) VALUES('CDLA-Permissive-1.0', '

Community Data License Agreement - Permissive - Version 1.0

This is the Community Data License Agreement - Permissive, Version 1.0 ("Agreement").  Data is provided to You under this Agreement by each of the Data Providers.  Your exercise of any of the rights and permissions granted below constitutes Your acceptance and agreement to be bound by the terms and conditions of this Agreement.

The benefits that each Data Provider receives from making Data available and that You receive from Data or otherwise under these terms and conditions shall be deemed sufficient consideration for the formation of this Agreement.  Accordingly, Data Provider(s) and You (the "Parties") agree as follows:

Section 1.  Definitions

1.1 "Add" means to supplement Data with Your own or someone else’s Data, resulting in Your "Additions."  Additions do not include Results.

1.2 "Computational Use" means Your analysis (through the use of computational devices or otherwise) or other interpretation of Data.  By way of example and not limitation, "Computational Use" includes the application of any computational analytical technique, the purpose of which is the analysis of any Data in digital form to generate information about Data such as patterns, trends, correlations, inferences, insights and attributes.

1.3 "Data" means the information (including copyrightable information, such as images or text), collectively or individually, whether created or gathered by a Data Provider or an Entity acting on its behalf, to which rights are granted under this Agreement.

1.4 "Data Provider" means any Entity (including any employee or contractor of such Entity authorized to Publish Data on behalf of such Entity) that Publishes Data under this Agreement prior to Your Receiving it.

1.5 "Enhanced Data" means the subset of Data that You Publish and that is composed of (a) Your Additions and/or (b) Modifications to Data You have received under this Agreement.

1.6 "Entity" means any natural person or organization that exists under the laws of the jurisdiction in which it is organized, together with all other entities that control, are controlled by, or are under common control with that entity.  For the purposes of this definition, "control" means (a) the power, directly or indirectly, to cause the direction or management of such entity, whether by contract or otherwise, (b) the ownership of more than fifty percent (50%) of the outstanding shares or securities, (c) the beneficial ownership of such entity or, (d) the ability to appoint, whether by agreement or right, the majority of directors of an Entity.

1.7 "Modify" means to delete, erase, correct or re-arrange Data, resulting in "Modifications."  Modifications do not include Results.

1.8 "Publish" means to make all or a subset of Data (including Your Enhanced Data) available in any manner which enables its Use, including by providing a copy on physical media or remote access.  For any form of Entity, that is to make the Data available to any individual who is not employed by that Entity or engaged as a contractor or agent to perform work on that Entity’s behalf.  A "Publication" occurs each time You Publish Data.

1.9 "Receive" or "Receives" means to have been given access to Data, locally or remotely.

1.10 "Results" means the outcomes or outputs that You obtain from Your Computational Use of Data.  Results shall not include more than a de minimis portion of the Data on which the Computational Use is based.

1.11 "Sui Generis Database Rights" means rights, other than copyright, resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other equivalent rights anywhere in the world.

1.12 "Use" means using Data (including accessing, copying, studying, reviewing, adapting, analyzing, evaluating, or making Computational Use of it), either by machines or humans, or a combination of both.

1.13 "You" or "Your" means any Entity that Receives Data under this Agreement.

Section 2.  Right and License to Use and to Publish

2.1 Subject to the conditions set forth in Section 3 of this Agreement, Data Provider(s) hereby grant(s) to You a worldwide, non-exclusive, irrevocable (except as provided in Section 5) right to: (a) Use Data; and (b) Publish Data.

2.2 To the extent that the Data or the coordination, selection or arrangement of Data is protected or protectable under copyright, Sui Generis Database Rights, or other law, Data Provider(s) further agree(s) that such Data or coordination, selection or arrangement is hereby licensed to You and to anyone else who Receives Data under this Agreement for Use and Publication, subject to the conditions set forth in Section 3 of this Agreement.

2.3 Except for these rights and licenses expressly granted, no other intellectual property rights are granted or should be implied.

Section 3.  Conditions on Rights Granted

3.1 If You Publish Data You Receive or Enhanced Data:

(a) You may do so under a license of Your choice provided that You give anyone who Receives the Data from You the text of this Agreement, the name of this Agreement and/or a hyperlink or other method reasonably likely to provide a copy of the text of this Agreement; and

(b) You must cause any Data files containing Enhanced Data to carry prominent notices that You have changed those files; and

(c) If You Publish Data You Receive, You must preserve all credit or attribution to the Data Provider(s). Such retained credit or attribution includes any of the following to the extent they exist in Data as You have Received it: legal notices or metadata; identification of the Data Provider(s); or hyperlinks to Data to the extent it is practical to do so.

3.2 You may provide additional or different license terms and conditions for use, reproduction, or distribution of that Enhanced Data, or for any combination of Data and Enhanced Data as a whole, provided that Your Use and Publication of that combined Data otherwise complies with the conditions stated in this License.

3.3 You and each Data Provider agree that Enhanced Data shall not be considered a work of joint authorship by virtue of its relationship to Data licensed under this Agreement and shall not require either any obligation of accounting to or the consent of any Data Provider.

3.4 This Agreement imposes no obligations or restrictions on Your Use or Publication of Results.

Section 4.  Data Provider(s)’ Representations

4.1 Each Data Provider represents that the Data Provider has exercised reasonable care, to assure that: (a) the Data it Publishes was created or generated by it or was obtained from others with the right to Publish the Data under this Agreement; and (b) Publication of such Data does not violate any privacy or confidentiality obligation undertaken by the Data Provider.

Section 5.  Termination

5.1 All of Your rights under this Agreement will terminate, and Your right to Receive, Use or Publish the Data will be revoked or modified if You materially fail to comply with the terms and conditions of this Agreement and You do not cure such failure in a reasonable period of time after becoming aware of such noncompliance.  If Your rights under this Agreement terminate, You agree to cease Receipt, Use and Publication of Data.  However, Your obligations and any rights and permissions granted by You under this Agreement relating to Data that You Published prior to such termination will continue and survive.

5.2 If You institute litigation against a Data Provider or anyone else who Receives the Data (including a cross-claim in a lawsuit) based on the Data, other than a claim asserting breach of this Agreement, then any rights previously granted to You to Receive, Use and Publish Data under this Agreement will terminate as of the date such litigation is filed.

Section 6.  Disclaimer of Warranties and Limitation of Liability

6.1 EXCEPT AS EXPRESSLY SET FORTH IN THIS AGREEMENT, THE DATA (INCLUDING ENHANCED DATA) IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, EITHER EXPRESS OR IMPLIED INCLUDING, WITHOUT LIMITATION, ANY WARRANTIES OR CONDITIONS OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.

6.2 NEITHER YOU NOR ANY DATA PROVIDERS SHALL HAVE ANY LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING WITHOUT LIMITATION LOST PROFITS), HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OR DISTRIBUTION OF THE DATA OR THE EXERCISE OF ANY RIGHTS GRANTED HEREUNDER, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.

Section 7.  Miscellaneous

7.1 You agree that it is solely Your responsibility to comply with all applicable laws with regard to Your Use or Publication of Data, including any applicable privacy, data protection, security and export laws.  You agree to take reasonable steps to assist a Data Provider fulfilling responsibilities to comply with applicable laws with regard to Use or Publication of Data Received hereunder.

7.2 You and Data Provider(s), collectively and individually, waive and/or agree not to assert, to the extent permitted by law, any moral rights You or they hold in Data.

7.3 This Agreement confers no rights or remedies upon any person or entity other than the Parties and their respective heirs, executors, successors and assigns.

7.4 The Data Provider(s) reserve no right or expectation of privacy, data protection or confidentiality in any Data that they Publish under this Agreement.  If You choose to Publish Data under this Agreement, You similarly do so with no reservation or expectation of any rights of privacy or confidentiality in that Data.

7.5 The Community Data License Agreement workgroup under The Linux Foundation is the steward of this Agreement ("Steward").  No one other than the Steward has the right to modify or publish new versions of this Agreement.  Each version will be given a distinguishing version number.  You may Use and Publish Data Received hereunder under the terms of the version of the Agreement under which You originally Received the Data, or under the terms of any subsequent version published by the Steward.
', 'https://cdla.io/permissive-1-0/');

INSERT IGNORE INTO license(name, description, url) VALUES('ODC-BY', 'ODC Attribution License (ODC-By)
Preamble

The Open Data Commons Attribution License is a license agreement
intended to allow users to freely share, modify, and use this Database
subject only to the attribution requirements set out in Section 4.

Databases can contain a wide variety of types of content (images,
audiovisual material, and sounds all in the same database, for example),
and so this license only governs the rights over the Database, and not
the contents of the Database individually. Licensors may therefore wish
to use this license together with another license for the contents.

Sometimes the contents of a database, or the database itself, can be
covered by other rights not addressed here (such as private contracts,
trademark over the name, or privacy rights / data protection rights
over information in the contents), and so you are advised that you may
have to consult other documents or clear other rights before doing
activities not covered by this License.

The Licensor (as defined below)

and

You (as defined below)

agree as follows:
1.0 Definitions of Capitalised Words

"Collective Database" - Means this Database in unmodified form as part
of a collection of independent databases in themselves that together are
assembled INTO a collective whole. A work that constitutes a Collective
Database will not be considered a Derivative Database.

"Convey" - As a verb, means Using the Database, a Derivative Database,
or the Database as part of a Collective Database in any way that enables
a Person to make or receive copies of the Database or a Derivative
Database. Conveying does not include interaction with a user through a
computer network, or creating and Using a Produced Work, where no
transfer of a copy of the Database or a Derivative Database occurs.

"Contents" - The contents of this Database, which includes the
information, independent works, or other material collected INTO the
Database. For example, the contents of the Database could be factual
data or works such as images, audiovisual material, text, or sounds.

"Database" - A collection of material (the Contents) arranged in a
systematic or methodical way and individually accessible by electronic
or other means offered under the terms of this License.

"Database Directive" - Means Directive 96/9/EC of the European
Parliament and of the Council of 11 March 1996 on the legal protection
of databases, as amended or succeeded.

"Database Right" - Means rights resulting from the Chapter III ("sui
generis") rights in the Database Directive (as amended and as transposed
by member states), which includes the Extraction and Re-utilisation of
the whole or a Substantial part of the Contents, as well as any similar
rights available in the relevant jurisdiction under Section 10.4.

"Derivative Database" - Means a database based upon the Database, and
includes any translation, adaptation, arrangement, modification, or any
other alteration of the Database or of a Substantial part of the
Contents. This includes, but is not limited to, Extracting or
Re-utilising the whole or a Substantial part of the Contents in a new
Database.

"Extraction" - Means the permanent or temporary transfer of all or a
Substantial part of the Contents to another medium by any means or in
any form.

"License" - Means this license agreement and is both a license of rights
such as copyright and Database Rights and an agreement in contract.

"Licensor" - Means the Person that offers the Database under the terms
of this License.

"Person" - Means a natural or legal person or a body of persons
corporate or incorporate.

"Produced Work" - a work (such as an image, audiovisual material, text,
or sounds) resulting from using the whole or a Substantial part of the
Contents (via a search or other query) from this Database, a Derivative
Database, or this Database as part of a Collective Database.

"Publicly" - means to Persons other than You or under Your control by
either more than 50% ownership or by the power to direct their
activities (such as contracting with an independent consultant).

"Re-utilisation" - means any form of making available to the public all
or a Substantial part of the Contents by the distribution of copies, by
renting, by online or other forms of transmission.

"Substantial" - Means substantial in terms of quantity or quality or a
combination of both. The repeated and systematic Extraction or
Re-utilisation of insubstantial parts of the Contents may amount to the
Extraction or Re-utilisation of a Substantial part of the Contents.

"Use" - As a verb, means doing any act that is restricted by copyright
or Database Rights whether in the original medium or any other; and
includes without limitation distributing, copying, publicly performing,
publicly displaying, and preparing derivative works of the Database, as
well as modifying the Database as may be technically necessary to use it
in a different mode or format.

"You" - Means a Person exercising rights under this License who has not
previously violated the terms of this License with respect to the
Database, or who has received express permission from the Licensor to
exercise rights under this License despite a previous violation.

Words in the singular include the plural and vice versa.
2.0 What this License covers

2.1. Legal effect of this document. This License is:

a. A license of applicable copyright and neighbouring rights;

b. A license of the Database Right; and

c. An agreement in contract between You and the Licensor.

2.2 Legal rights covered. This License covers the legal rights in the
Database, including:

a. Copyright. Any copyright or neighbouring rights in the Database.
The copyright licensed includes any individual elements of the
Database, but does not cover the copyright over the Contents
independent of this Database. See Section 2.4 for details. Copyright
law varies between jurisdictions, but is likely to cover: the Database
model or schema, which is the structure, arrangement, and organisation
of the Database, and can also include the Database tables and table
indexes; the data entry and output sheets; and the Field names of
Contents stored in the Database;

b. Database Rights. Database Rights only extend to the Extraction and
Re-utilisation of the whole or a Substantial part of the Contents.
Database Rights can apply even when there is no copyright over the
Database. Database Rights can also apply when the Contents are removed
from the Database and are selected and arranged in a way that would
not infringe any applicable copyright; and

c. Contract. This is an agreement between You and the Licensor for
access to the Database. In return you agree to certain conditions of
use on this access as outlined in this License.

2.3 Rights not covered.

a. This License does not apply to computer programs used in the making
or operation of the Database;

b. This License does not cover any patents over the Contents or the
Database; and

c. This License does not cover any trademarks associated with the
Database.

2.4 Relationship to Contents in the Database. The individual items of
the Contents contained in this Database may be covered by other rights,
including copyright, patent, data protection, privacy, or personality
rights, and this License does not cover any rights (other than Database
Rights or in contract) in individual Contents contained in the Database.
For example, if used on a Database of images (the Contents), this
License would not apply to copyright over individual images, which could
have their own separate licenses, or one single license covering all of
the rights over the images.
3.0 Rights granted

3.1 Subject to the terms and conditions of this License, the Licensor
grants to You a worldwide, royalty-free, non-exclusive, terminable (but
only under Section 9) license to Use the Database for the duration of
any applicable copyright and Database Rights. These rights explicitly
include commercial use, and do not exclude any field of endeavour. To
the extent possible in the relevant jurisdiction, these rights may be
exercised in all media and formats whether now known or created in the
future.

The rights granted cover, for example:

a. Extraction and Re-utilisation of the whole or a Substantial part of
the Contents;

b. Creation of Derivative Databases;

c. Creation of Collective Databases;

d. Creation of temporary or permanent reproductions by any means and
in any form, in whole or in part, including of any Derivative
Databases or as a part of Collective Databases; and

e. Distribution, communication, display, lending, making available, or
performance to the public by any means and in any form, in whole or in
part, including of any Derivative Database or as a part of Collective
Databases.

3.2 Compulsory license schemes. For the avoidance of doubt:

a. Non-waivable compulsory license schemes. In those jurisdictions in
which the right to collect royalties through any statutory or
compulsory licensing scheme cannot be waived, the Licensor reserves
the exclusive right to collect such royalties for any exercise by You
of the rights granted under this License;

b. Waivable compulsory license schemes. In those jurisdictions in
which the right to collect royalties through any statutory or
compulsory licensing scheme can be waived, the Licensor waives the
exclusive right to collect such royalties for any exercise by You of
the rights granted under this License; and,

c. Voluntary license schemes. The Licensor waives the right to collect
royalties, whether individually or, in the event that the Licensor is
a member of a collecting society that administers voluntary licensing
schemes, via that society, from any exercise by You of the rights
granted under this License.

3.3 The right to release the Database under different terms, or to stop
distributing or making available the Database, is reserved. Note that
this Database may be multiple-licensed, and so You may have the choice
of using alternative licenses for this Database. Subject to Section
10.4, all other rights not expressly granted by Licensor are reserved.
4.0 Conditions of Use

4.1 The rights granted in Section 3 above are expressly made subject to
Your complying with the following conditions of use. These are important
conditions of this License, and if You fail to follow them, You will be
in material breach of its terms.

4.2 Notices. If You Publicly Convey this Database, any Derivative
Database, or the Database as part of a Collective Database, then You
must:

a. Do so only under the terms of this License;

b. Include a copy of this License or its Uniform Resource Identifier (URI)
with the Database or Derivative Database, including both in the
Database or Derivative Database and in any relevant documentation;

c. Keep intact any copyright or Database Right notices and notices
that refer to this License; and

d. If it is not possible to put the required notices in a particular
file due to its structure, then You must include the notices in a
location (such as a relevant directory) where users would be likely to
look for it.

4.3 Notice for using output (Contents). Creating and Using a Produced
Work does not require the notice in Section 4.2. However, if you
Publicly Use a Produced Work, You must include a notice associated with
the Produced Work reasonably calculated to make any Person that uses,
views, accesses, interacts with, or is otherwise exposed to the Produced
Work aware that Content was obtained from the Database, Derivative
Database, or the Database as part of a Collective Database, and that it
is available under this License.

a. Example notice. The following text will satisfy notice under
Section 4.3:

    Contains information from DATABASE NAME which is made available
    under the ODC Attribution License.

DATABASE NAME should be replaced with the name of the Database and a
hyperlink to the location of the Database. "ODC Attribution License"
should contain a hyperlink to the URI of the text of this License. If
hyperlinks are not possible, You should include the plain text of the
required URI’s with the above notice.

4.4 Licensing of others. You may not sublicense the Database. Each time
You communicate the Database, the whole or Substantial part of the
Contents, or any Derivative Database to anyone else in any way, the
Licensor offers to the recipient a license to the Database on the same
terms and conditions as this License. You are not responsible for
enforcing compliance by third parties with this License, but You may
enforce any rights that You have over a Derivative Database. You are
solely responsible for any modifications of a Derivative Database made
by You or another Person at Your direction. You may not impose any
further restrictions on the exercise of the rights granted or affirmed
under this License.
5.0 Moral rights

5.1 Moral rights. This section covers moral rights, including any rights
to be identified as the author of the Database or to object to treatment
that would otherwise prejudice the author’s honour and reputation, or
any other derogatory treatment:

a. For jurisdictions allowing waiver of moral rights, Licensor waives
all moral rights that Licensor may have in the Database to the fullest
extent possible by the law of the relevant jurisdiction under Section
10.4;

b. If waiver of moral rights under Section 5.1 a in the relevant
jurisdiction is not possible, Licensor agrees not to assert any moral
rights over the Database and waives all claims in moral rights to the
fullest extent possible by the law of the relevant jurisdiction under
Section 10.4; and

c. For jurisdictions not allowing waiver or an agreement not to assert
moral rights under Section 5.1 a and b, the author may retain their
moral rights over certain aspects of the Database.

Please note that some jurisdictions do not allow for the waiver of moral
rights, and so moral rights may still subsist over the Database in some
jurisdictions.
6.0 Fair dealing, Database exceptions, and other rights not affected

6.1 This License does not affect any rights that You or anyone else may
independently have under any applicable law to make any use of this
Database, including without limitation:

a. Exceptions to the Database Right including: Extraction of Contents
from non-electronic Databases for private purposes, Extraction for
purposes of illustration for teaching or scientific research, and
Extraction or Re-utilisation for public security or an administrative
or judicial procedure.

b. Fair dealing, fair use, or any other legally recognised limitation
or exception to infringement of copyright or other applicable laws.

6.2 This License does not affect any rights of lawful users to Extract
and Re-utilise insubstantial parts of the Contents, evaluated
quantitatively or qualitatively, for any purposes whatsoever, including
creating a Derivative Database (subject to other rights over the
Contents, see Section 2.4). The repeated and systematic Extraction or
Re-utilisation of insubstantial parts of the Contents may however amount
to the Extraction or Re-utilisation of a Substantial part of the
Contents.
7.0 Warranties and Disclaimer

7.1 The Database is licensed by the Licensor "as is" and without any
warranty of any kind, either express, implied, or arising by statute,
custom, course of dealing, or trade usage. Licensor specifically
disclaims any and all implied warranties or conditions of title,
non-infringement, accuracy or completeness, the presence or absence of
errors, fitness for a particular purpose, merchantability, or otherwise.
Some jurisdictions do not allow the exclusion of implied warranties, so
this exclusion may not apply to You.
8.0 Limitation of liability

8.1 Subject to any liability that may not be excluded or limited by law,
the Licensor is not liable for, and expressly excludes, all liability
for loss or damage however and whenever caused to anyone by any use
under this License, whether by You or by anyone else, and whether caused
by any fault on the part of the Licensor or not. This exclusion of
liability includes, but is not limited to, any special, incidental,
consequential, punitive, or exemplary damages such as loss of revenue,
data, anticipated profits, and lost business. This exclusion applies
even if the Licensor has been advised of the possibility of such
damages.

8.2 If liability may not be excluded by law, it is limited to actual and
direct financial loss to the extent it is caused by proved negligence on
the part of the Licensor.
9.0 Termination of Your rights under this License

9.1 Any breach by You of the terms and conditions of this License
automatically terminates this License with immediate effect and without
notice to You. For the avoidance of doubt, Persons who have received the
Database, the whole or a Substantial part of the Contents, Derivative
Databases, or the Database as part of a Collective Database from You
under this License will not have their licenses terminated provided
their use is in full compliance with this License or a license granted
under Section 4.8 of this License. Sections 1, 2, 7, 8, 9 and 10 will
survive any termination of this License.

9.2 If You are not in breach of the terms of this License, the Licensor
will not terminate Your rights under it.

9.3 Unless terminated under Section 9.1, this License is granted to You
for the duration of applicable rights in the Database.

9.4 Reinstatement of rights. If you cease any breach of the terms and
conditions of this License, then your full rights under this License
will be reinstated:

a. Provisionally and subject to permanent termination until the 60th
day after cessation of breach;

b. Permanently on the 60th day after cessation of breach unless
otherwise reasonably notified by the Licensor; or

c. Permanently if reasonably notified by the Licensor of the
violation, this is the first time You have received notice of
violation of this License from the Licensor, and You cure the
violation prior to 30 days after your receipt of the notice.

9.5 Notwithstanding the above, Licensor reserves the right to release
the Database under different license terms or to stop distributing or
making available the Database. Releasing the Database under different
license terms or stopping the distribution of the Database will not
withdraw this License (or any other license that has been, or is
required to be, granted under the terms of this License), and this
License will continue in full force and effect unless terminated as
stated above.
10.0 General

10.1 If any provision of this License is held to be invalid or
unenforceable, that must not affect the validity or enforceability of
the remainder of the terms and conditions of this License and each
remaining provision of this License shall be valid and enforced to the
fullest extent permitted by law.

10.2 This License is the entire agreement between the parties with
respect to the rights granted here over the Database. It replaces any
earlier understandings, agreements or representations with respect to
the Database.

10.3 If You are in breach of the terms of this License, You will not be
entitled to rely on the terms of this License or to complain of any
breach by the Licensor.

10.4 Choice of law. This License takes effect in and will be governed by
the laws of the relevant jurisdiction in which the License terms are
sought to be enforced. If the standard suite of rights granted under
applicable copyright law and Database Rights in the relevant
jurisdiction includes additional rights not granted under this License,
these additional rights are granted in this License in order to meet the
terms of this License.', 'https://opendatacommons.org/licenses/by/1.0/index.html');

INSERT IGNORE INTO license(name, description, url) VALUES('CC-BY-SA', "Creative Commons Attribution-ShareAlike 4.0 International Public License

By exercising the Licensed Rights (defined below), You accept and agree to be bound by the terms and conditions of this Creative Commons Attribution-ShareAlike 4.0 International Public License (Public License). To the extent this Public License may be interpreted as a contract, You are granted the Licensed Rights in consideration of Your acceptance of these terms and conditions, and the Licensor grants You such rights in consideration of benefits the Licensor receives from making the Licensed Material available under these terms and conditions.

Section 1 - Definitions.

    Adapted Material means material subject to Copyright and Similar Rights that is derived from or based upon the Licensed Material and in which the Licensed Material is translated, altered, arranged, transformed, or otherwise modified in a manner requiring permission under the Copyright and Similar Rights held by the Licensor. For purposes of this Public License, where the Licensed Material is a musical work, performance, or sound recording, Adapted Material is always produced where the Licensed Material is synched in timed relation with a moving image.
    Adapter's License means the license You apply to Your Copyright and Similar Rights in Your contributions to Adapted Material in accordance with the terms and conditions of this Public License.
    BY-SA Compatible License means a license listed at creativecommons.org/compatiblelicenses, approved by Creative Commons as essentially the equivalent of this Public License.
    Copyright and Similar Rights means copyright and/or similar rights closely related to copyright including, without limitation, performance, broadcast, sound recording, and Sui Generis Database Rights, without regard to how the rights are labeled or categorized. For purposes of this Public License, the rights specified in Section 2(b)(1)-(2) are not Copyright and Similar Rights.
    Effective Technological Measures means those measures that, in the absence of proper authority, may not be circumvented under laws fulfilling obligations under Article 11 of the WIPO Copyright Treaty adopted on December 20, 1996, and/or similar international agreements.
    Exceptions and Limitations means fair use, fair dealing, and/or any other exception or limitation to Copyright and Similar Rights that applies to Your use of the Licensed Material.
    License Elements means the license attributes listed in the name of a Creative Commons Public License. The License Elements of this Public License are Attribution and ShareAlike.
    Licensed Material means the artistic or literary work, database, or other material to which the Licensor applied this Public License.
    Licensed Rights means the rights granted to You subject to the terms and conditions of this Public License, which are limited to all Copyright and Similar Rights that apply to Your use of the Licensed Material and that the Licensor has authority to license.
    Licensor means the individual(s) or entity(ies) granting rights under this Public License.
    Share means to provide material to the public by any means or process that requires permission under the Licensed Rights, such as reproduction, public display, public performance, distribution, dissemination, communication, or importation, and to make material available to the public including in ways that members of the public may access the material from a place and at a time individually chosen by them.
    Sui Generis Database Rights means rights other than copyright resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other essentially equivalent rights anywhere in the world.
    You means the individual or entity exercising the Licensed Rights under this Public License. Your has a corresponding meaning.

Section 2 - Scope.

    License grant.
        Subject to the terms and conditions of this Public License, the Licensor hereby grants You a worldwide, royalty-free, non-sublicensable, non-exclusive, irrevocable license to exercise the Licensed Rights in the Licensed Material to:
            reproduce and Share the Licensed Material, in whole or in part; and
            produce, reproduce, and Share Adapted Material.
        Exceptions and Limitations. For the avoidance of doubt, where Exceptions and Limitations apply to Your use, this Public License does not apply, and You do not need to comply with its terms and conditions.
        Term. The term of this Public License is specified in Section 6(a).
        Media and formats; technical modifications allowed. The Licensor authorizes You to exercise the Licensed Rights in all media and formats whether now known or hereafter created, and to make technical modifications necessary to do so. The Licensor waives and/or agrees not to assert any right or authority to forbid You from making technical modifications necessary to exercise the Licensed Rights, including technical modifications necessary to circumvent Effective Technological Measures. For purposes of this Public License, simply making modifications authorized by this Section 2(a)(4) never produces Adapted Material.
        Downstream recipients.
            Offer from the Licensor - Licensed Material. Every recipient of the Licensed Material automatically receives an offer from the Licensor to exercise the Licensed Rights under the terms and conditions of this Public License.
            Additional offer from the Licensor - Adapted Material. Every recipient of Adapted Material from You automatically receives an offer from the Licensor to exercise the Licensed Rights in the Adapted Material under the conditions of the Adapter’s License You apply.
            No downstream restrictions. You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, the Licensed Material if doing so restricts exercise of the Licensed Rights by any recipient of the Licensed Material.
        No endorsement. Nothing in this Public License constitutes or may be construed as permission to assert or imply that You are, or that Your use of the Licensed Material is, connected with, or sponsored, endorsed, or granted official status by, the Licensor or others designated to receive attribution as provided in Section 3(a)(1)(A)(i).

    Other rights.
        Moral rights, such as the right of integrity, are not licensed under this Public License, nor are publicity, privacy, and/or other similar personality rights; however, to the extent possible, the Licensor waives and/or agrees not to assert any such rights held by the Licensor to the limited extent necessary to allow You to exercise the Licensed Rights, but not otherwise.
        Patent and trademark rights are not licensed under this Public License.
        To the extent possible, the Licensor waives any right to collect royalties from You for the exercise of the Licensed Rights, whether directly or through a collecting society under any voluntary or waivable statutory or compulsory licensing scheme. In all other cases the Licensor expressly reserves any right to collect such royalties.

Section 3 - License Conditions.

Your exercise of the Licensed Rights is expressly made subject to the following conditions.

    Attribution.

        If You Share the Licensed Material (including in modified form), You must:
            retain the following if it is supplied by the Licensor with the Licensed Material:
                identification of the creator(s) of the Licensed Material and any others designated to receive attribution, in any reasonable manner requested by the Licensor (including by pseudonym if designated);
                a copyright notice;
                a notice that refers to this Public License;
                a notice that refers to the disclaimer of warranties;
                a URI or hyperlink to the Licensed Material to the extent reasonably practicable;
            indicate if You modified the Licensed Material and retain an indication of any previous modifications; and
            indicate the Licensed Material is licensed under this Public License, and include the text of, or the URI or hyperlink to, this Public License.
        You may satisfy the conditions in Section 3(a)(1) in any reasonable manner based on the medium, means, and context in which You Share the Licensed Material. For example, it may be reasonable to satisfy the conditions by providing a URI or hyperlink to a resource that includes the required information.
        If requested by the Licensor, You must remove any of the information required by Section 3(a)(1)(A) to the extent reasonably practicable.
    ShareAlike.

    In addition to the conditions in Section 3(a), if You Share Adapted Material You produce, the following conditions also apply.
        The Adapter’s License You apply must be a Creative Commons license with the same License Elements, this version or later, or a BY-SA Compatible License.
        You must include the text of, or the URI or hyperlink to, the Adapter's License You apply. You may satisfy this condition in any reasonable manner based on the medium, means, and context in which You Share Adapted Material.
        You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, Adapted Material that restrict exercise of the rights granted under the Adapter's License You apply.

Section 4 - Sui Generis Database Rights.

Where the Licensed Rights include Sui Generis Database Rights that apply to Your use of the Licensed Material:

    for the avoidance of doubt, Section 2(a)(1) grants You the right to extract, reuse, reproduce, and Share all or a substantial portion of the contents of the database;
    if You include all or a substantial portion of the database contents in a database in which You have Sui Generis Database Rights, then the database in which You have Sui Generis Database Rights (but not its individual contents) is Adapted Material, including for purposes of Section 3(b); and
    You must comply with the conditions in Section 3(a) if You Share all or a substantial portion of the contents of the database.

For the avoidance of doubt, this Section 4 supplements and does not replace Your obligations under this Public License where the Licensed Rights include other Copyright and Similar Rights.

Section 5 - Disclaimer of Warranties and Limitation of Liability.

    Unless otherwise separately undertaken by the Licensor, to the extent possible, the Licensor offers the Licensed Material as-is and as-available, and makes no representations or warranties of any kind concerning the Licensed Material, whether express, implied, statutory, or other. This includes, without limitation, warranties of title, merchantability, fitness for a particular purpose, non-infringement, absence of latent or other defects, accuracy, or the presence or absence of errors, whether or not known or discoverable. Where disclaimers of warranties are not allowed in full or in part, this disclaimer may not apply to You.
    To the extent possible, in no event will the Licensor be liable to You on any legal theory (including, without limitation, negligence) or otherwise for any direct, special, indirect, incidental, consequential, punitive, exemplary, or other losses, costs, expenses, or damages arising out of this Public License or use of the Licensed Material, even if the Licensor has been advised of the possibility of such losses, costs, expenses, or damages. Where a limitation of liability is not allowed in full or in part, this limitation may not apply to You.

    The disclaimer of warranties and limitation of liability provided above shall be interpreted in a manner that, to the extent possible, most closely approximates an absolute disclaimer and waiver of all liability.

Section 6 - Term and Termination.

    This Public License applies for the term of the Copyright and Similar Rights licensed here. However, if You fail to comply with this Public License, then Your rights under this Public License terminate automatically.

    Where Your right to use the Licensed Material has terminated under Section 6(a), it reinstates:
        automatically as of the date the violation is cured, provided it is cured within 30 days of Your discovery of the violation; or
        upon express reinstatement by the Licensor.
    For the avoidance of doubt, this Section 6(b) does not affect any right the Licensor may have to seek remedies for Your violations of this Public License.
    For the avoidance of doubt, the Licensor may also offer the Licensed Material under separate terms or conditions or stop distributing the Licensed Material at any time; however, doing so will not terminate this Public License.
    Sections 1, 5, 6, 7, and 8 survive termination of this Public License.

Section 7 - Other Terms and Conditions.

    The Licensor shall not be bound by any additional or different terms or conditions communicated by You unless expressly agreed.
    Any arrangements, understandings, or agreements regarding the Licensed Material not stated herein are separate from and independent of the terms and conditions of this Public License.

Section 8 - Interpretation.

    For the avoidance of doubt, this Public License does not, and shall not be interpreted to, reduce, limit, restrict, or impose conditions on any use of the Licensed Material that could lawfully be made without permission under this Public License.
    To the extent possible, if any provision of this Public License is deemed unenforceable, it shall be automatically reformed to the minimum extent necessary to make it enforceable. If the provision cannot be reformed, it shall be severed from this Public License without affecting the enforceability of the remaining terms and conditions.
    No term or condition of this Public License will be waived and no failure to comply consented to unless expressly agreed to by the Licensor.
    Nothing in this Public License constitutes or may be interpreted as a limitation upon, or waiver of, any privileges and immunities that apply to the Licensor or You, including from the legal processes of any jurisdiction or authority.
", 'https://creativecommons.org/licenses/by-sa/4.0/legalcode');

INSERT IGNORE INTO license(name, description, url) VALUES('CDLA-Sharing-1.0', '

Community Data License Agreement - Sharing - Version 1.0

This is the Community Data License Agreement - Sharing, Version 1.0 ("Agreement").  Data is provided to You under this Agreement by each of the Data Providers.  Your exercise of any of the rights and permissions granted below constitutes Your acceptance and agreement to be bound by the terms and conditions of this Agreement.

The benefits that each Data Provider receives from making Data available and that You receive from Data or otherwise under these terms and conditions shall be deemed sufficient consideration for the formation of this Agreement.  Accordingly, Data Provider(s) and You (the "Parties") agree as follows:

Section 1.  Definitions

1.1 "Add" means to supplement Data with Your own or someone else’s Data, resulting in Your "Additions."  Additions do not include Results.

1.2 "Computational Use" means Your analysis (through the use of computational devices or otherwise) or other interpretation of Data.  By way of example and not limitation, "Computational Use" includes the application of any computational analytical technique, the purpose of which is the analysis of any Data in digital form to generate information about Data such as patterns, trends, correlations, inferences, insights and attributes.

1.3 "Data" means the information (including copyrightable information, such as images or text), collectively or individually, whether created or gathered by a Data Provider or an Entity acting on its behalf, to which rights are granted under this Agreement.

1.4 "Data Provider" means any Entity (including any employee or contractor of such Entity authorized to Publish Data on behalf of such Entity) that Publishes Data under this Agreement prior to Your Receiving it.

1.5 "Enhanced Data" means the subset of Data that You Publish and that is composed of (a) Your Additions and/or (b) Modifications to Data You have received under this Agreement.

1.6 "Entity" means any natural person or organization that exists under the laws of the jurisdiction in which it is organized, together with all other entities that control, are controlled by, or are under common control with that entity.  For the purposes of this definition, "control" means (a) the power, directly or indirectly, to cause the direction or management of such entity, whether by contract or otherwise, (b) the ownership of more than fifty percent (50%) of the outstanding shares or securities, (c) the beneficial ownership of such entity or, (d) the ability to appoint, whether by agreement or right, the majority of directors of an Entity.

1.7 "Ledger" means a digital record of Data or grants of rights in Data governed by this Agreement, using any technology having functionality to record and store Data or grants, contributions, or licenses to Data governed by this Agreement.

1.8 "Modify" means to delete, erase, correct or re-arrange Data, resulting in "Modifications."  Modifications do not include Results.

1.9 "Publish" means to make all or a subset of Data (including Your Enhanced Data) available in any manner which enables its Use, including by providing a copy on physical media or remote access.  For any form of Entity, that is to make the Data available to any individual who is not employed by that Entity or engaged as a contractor or agent to perform work on that Entity’s behalf.  A "Publication" occurs each time You Publish Data.

1.10 "Receive" or "Receives" means to have been given access to Data, locally or remotely.

1.11 "Results" means the outcomes or outputs that You obtain from Your Computational Use of Data.  Results shall not include more than a de minimis portion of the Data on which the Computational Use is based.

1.12 "Sui Generis Database Rights" means rights, other than copyright, resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other equivalent rights anywhere in the world.

1.13 "Use" means using Data (including accessing, copying, studying, reviewing, adapting, analyzing, evaluating, or making Computational Use of it), either by machines or humans, or a combination of both.

1.14 "You" or "Your" means any Entity that Receives Data under this Agreement.

Section 2.  Right and License to Use and to Publish

2.1 Subject to the conditions set forth in Section 3 of this Agreement, Data Provider(s) hereby grant(s) to You a worldwide, non-exclusive, irrevocable (except as provided in Section 5) right to: (a) Use Data; and (b) Publish Data.

2.2 To the extent that the Data or the coordination, selection or arrangement of Data is protected or protectable under copyright, Sui Generis Database Rights, or other law, Data Provider(s) further agree(s) that such Data or coordination, selection or arrangement is hereby licensed to You and to anyone else who Receives Data under this Agreement for Use and Publication, subject to the conditions set forth in Section 3 of this Agreement.

2.3 Except for these rights and licenses expressly granted, no other intellectual property rights are granted or should be implied.

Section 3.  Conditions on Rights Granted

3.1 If You Publish Data You Receive or Enhanced Data:

(a) The Data (including the Enhanced Data) must be Published under this Agreement in accordance with this Section 3; and

(b) You must cause any Data files containing Enhanced Data to carry prominent notices that You have changed those files; and

(c) If You Publish Data You Receive, You must preserve all credit or attribution to the Data Provider(s). Such retained credit or attribution includes any of the following to the extent they exist in Data as You have Received it: legal notices or metadata; identification of the Data Provider(s); or hyperlinks to Data to the extent it is practical to do so.

3.2 You may not restrict or deter the ability of anyone who Receives the Data (a) to Publish the Data in a publicly-accessible manner or (b) if the project has designated a Ledger for recording Data or grants of rights in Data for purposes of this Agreement, to record the Data or grants of rights in Data in the Ledger.

3.3 If You Publish Data You Receive, You must do so under an unmodified form of this Agreement and include the text of this Agreement, the name of this Agreement and/or a hyperlink or other method reasonably likely to provide a copy of the text of this Agreement.  You may not modify this Agreement or impose any further restrictions on the exercise of the rights granted under this Agreement, including by adding any restriction on commercial or non-commercial Use of Data (including Your Enhanced Data) or by limiting permitted Use of such Data to any particular platform, technology or field of endeavor.  Notices that purport to modify this Agreement shall be of no effect.

3.4 You and each Data Provider agree that Enhanced Data shall not be considered a work of joint authorship by virtue of its relationship to Data licensed under this Agreement and shall not require either any obligation of accounting to or the consent of any Data Provider.

3.5 This Agreement imposes no obligations or restrictions on Your Use or Publication of Results.

Section 4.  Data Provider(s)’ Representations

4.1 Each Data Provider represents that the Data Provider has exercised reasonable care, to assure that: (a) the Data it Publishes was created or generated by it or was obtained from others with the right to Publish the Data under this Agreement; and (b) Publication of such Data does not violate any privacy or confidentiality obligation undertaken by the Data Provider.

Section 5.  Termination

5.1 All of Your rights under this Agreement will terminate, and Your right to Receive, Use or Publish the Data will be revoked or modified if You materially fail to comply with the terms and conditions of this Agreement and You do not cure such failure in a reasonable period of time after becoming aware of such noncompliance.  If Your rights under this Agreement terminate, You agree to cease Receipt, Use and Publication of Data.  However, Your obligations and any rights and permissions granted by You under this Agreement relating to Data that You Published prior to such termination will continue and survive.

5.2 If You institute litigation against a Data Provider or anyone else who Receives the Data (including a cross-claim in a lawsuit) based on the Data, other than a claim asserting breach of this Agreement, then any rights previously granted to You to Receive, Use and Publish Data under this Agreement will terminate as of the date such litigation is filed.

Section 6.  Disclaimer of Warranties and Limitation of Liability

6.1 EXCEPT AS EXPRESSLY SET FORTH IN THIS AGREEMENT, THE DATA (INCLUDING ENHANCED DATA) IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, EITHER EXPRESS OR IMPLIED INCLUDING, WITHOUT LIMITATION, ANY WARRANTIES OR CONDITIONS OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.

6.2 NEITHER YOU NOR ANY DATA PROVIDERS SHALL HAVE ANY LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING WITHOUT LIMITATION LOST PROFITS), HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OR DISTRIBUTION OF THE DATA OR THE EXERCISE OF ANY RIGHTS GRANTED HEREUNDER, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.

Section 7.  Miscellaneous

7.1 You agree that it is solely Your responsibility to comply with all applicable laws with regard to Your Use or Publication of Data, including any applicable privacy, data protection, security and export laws.  You agree to take reasonable steps to assist a Data Provider fulfilling responsibilities to comply with applicable laws with regard to Use or Publication of Data Received hereunder.

7.2 You and Data Provider(s), collectively and individually, waive and/or agree not to assert, to the extent permitted by law, any moral rights You or they hold in Data.

7.3 This Agreement confers no rights or remedies upon any person or entity other than the Parties and their respective heirs, executors, successors and assigns.

7.4 The Data Provider(s) reserve no right or expectation of privacy, data protection or confidentiality in any Data that they Publish under this Agreement.  If You choose to Publish Data under this Agreement, You similarly do so with no reservation or expectation of any rights of privacy or confidentiality in that Data.

7.5 The Community Data License Agreement workgroup under The Linux Foundation is the steward of this Agreement ("Steward").  No one other than the Steward has the right to modify or publish new versions of this Agreement.  Each version will be given a distinguishing version number.  You may Use and Publish Data Received hereunder under the terms of the version of the Agreement under which You originally Received the Data, or under the terms of any subsequent version published by the Steward.
', 'https://cdla.io/sharing-1-0/');


INSERT IGNORE INTO license(name, description, url) VALUES('ODbL', 'ODC Open Database License (ODbL)
Preamble

The Open Database License (ODbL) is a license agreement intended to
allow users to freely share, modify, and use this Database while
maintaining this same freedom for others. Many databases are covered by
copyright, and therefore this document licenses these rights. Some
jurisdictions, mainly in the European Union, have specific rights that
cover databases, and so the ODbL addresses these rights, too. Finally,
the ODbL is also an agreement in contract for users of this Database to
act in certain ways in return for accessing this Database.

Databases can contain a wide variety of types of content (images,
audiovisual material, and sounds all in the same database, for example),
and so the ODbL only governs the rights over the Database, and not the
contents of the Database individually. Licensors should use the ODbL
together with another license for the contents, if the contents have a
single set of rights that uniformly covers all of the contents. If the
contents have multiple sets of different rights, Licensors should
describe what rights govern what contents together in the individual
record or in some other way that clarifies what rights apply.

Sometimes the contents of a database, or the database itself, can be
covered by other rights not addressed here (such as private contracts,
trade mark over the name, or privacy rights / data protection rights
over information in the contents), and so you are advised that you may
have to consult other documents or clear other rights before doing
activities not covered by this License.

The Licensor (as defined below)

and

You (as defined below)

agree as follows:
1.0 Definitions of Capitalised Words

"Collective Database" - Means this Database in unmodified form as part
of a collection of independent databases in themselves that together are
assembled INTO a collective whole. A work that constitutes a Collective
Database will not be considered a Derivative Database.

"Convey" - As a verb, means Using the Database, a Derivative Database,
or the Database as part of a Collective Database in any way that enables
a Person to make or receive copies of the Database or a Derivative
Database. Conveying does not include interaction with a user through a
computer network, or creating and Using a Produced Work, where no
transfer of a copy of the Database or a Derivative Database occurs.
"Contents" - The contents of this Database, which includes the
information, independent works, or other material collected INTO the
Database. For example, the contents of the Database could be factual
data or works such as images, audiovisual material, text, or sounds.

"Database" - A collection of material (the Contents) arranged in a
systematic or methodical way and individually accessible by electronic
or other means offered under the terms of this License.

"Database Directive" - Means Directive 96/9/EC of the European
Parliament and of the Council of 11 March 1996 on the legal protection
of databases, as amended or succeeded.

"Database Right" - Means rights resulting from the Chapter III ("sui
generis") rights in the Database Directive (as amended and as transposed
by member states), which includes the Extraction and Re-utilisation of
the whole or a Substantial part of the Contents, as well as any similar
rights available in the relevant jurisdiction under Section 10.4.

"Derivative Database" - Means a database based upon the Database, and
includes any translation, adaptation, arrangement, modification, or any
other alteration of the Database or of a Substantial part of the
Contents. This includes, but is not limited to, Extracting or
Re-utilising the whole or a Substantial part of the Contents in a new
Database.

"Extraction" - Means the permanent or temporary transfer of all or a
Substantial part of the Contents to another medium by any means or in
any form.

"License" - Means this license agreement and is both a license of rights
such as copyright and Database Rights and an agreement in contract.

"Licensor" - Means the Person that offers the Database under the terms
of this License.

"Person" - Means a natural or legal person or a body of persons
corporate or incorporate.

"Produced Work" - a work (such as an image, audiovisual material, text,
or sounds) resulting from using the whole or a Substantial part of the
Contents (via a search or other query) from this Database, a Derivative
Database, or this Database as part of a Collective Database.

"Publicly" - means to Persons other than You or under Your control by
either more than 50% ownership or by the power to direct their
activities (such as contracting with an independent consultant).

"Re-utilisation" - means any form of making available to the public all
or a Substantial part of the Contents by the distribution of copies, by
renting, by online or other forms of transmission.

"Substantial" - Means substantial in terms of quantity or quality or a
combination of both. The repeated and systematic Extraction or
Re-utilisation of insubstantial parts of the Contents may amount to the
Extraction or Re-utilisation of a Substantial part of the Contents.

"Use" - As a verb, means doing any act that is restricted by copyright
or Database Rights whether in the original medium or any other; and
includes without limitation distributing, copying, publicly performing,
publicly displaying, and preparing derivative works of the Database, as
well as modifying the Database as may be technically necessary to use it
in a different mode or format.

"You" - Means a Person exercising rights under this License who has not
previously violated the terms of this License with respect to the
Database, or who has received express permission from the Licensor to
exercise rights under this License despite a previous violation.

Words in the singular include the plural and vice versa.
2.0 What this License covers

2.1. Legal effect of this document. This License is:

      a. A license of applicable copyright and neighbouring rights;

      b. A license of the Database Right; and

      c. An agreement in contract between You and the Licensor.

2.2 Legal rights covered. This License covers the legal rights in the
Database, including:

      a. Copyright. Any copyright or neighbouring rights in the Database.
The copyright licensed includes any individual elements of the
Database, but does not cover the copyright over the Contents
independent of this Database. See Section 2.4 for details. Copyright
law varies between jurisdictions, but is likely to cover: the Database
model or schema, which is the structure, arrangement, and organisation
of the Database, and can also include the Database tables and table
indexes; the data entry and output sheets; and the Field names of
Contents stored in the Database;

      b. Database Rights. Database Rights only extend to the Extraction and
Re-utilisation of the whole or a Substantial part of the Contents.
Database Rights can apply even when there is no copyright over the
      Database. Database Rights can also apply when the Contents are removed
from the Database and are selected and arranged in a way that would
not infringe any applicable copyright; and

      c. Contract. This is an agreement between You and the Licensor for
access to the Database. In return you agree to certain conditions of
use on this access as outlined in this License.

2.3 Rights not covered.

      a. This License does not apply to computer programs used in the making
or operation of the Database;

      b. This License does not cover any patents over the Contents or the
Database; and

      c. This License does not cover any trademarks associated with the
      Database.

2.4 Relationship to Contents in the Database. The individual items of
the Contents contained in this Database may be covered by other rights,
including copyright, patent, data protection, privacy, or personality
rights, and this License does not cover any rights (other than Database
Rights or in contract) in individual Contents contained in the Database.
For example, if used on a Database of images (the Contents), this
License would not apply to copyright over individual images, which could
have their own separate licenses, or one single license covering all of
the rights over the images.
3.0 Rights granted

3.1 Subject to the terms and conditions of this License, the Licensor
grants to You a worldwide, royalty-free, non-exclusive, terminable (but
only under Section 9) license to Use the Database for the duration of
any applicable copyright and Database Rights. These rights explicitly
include commercial use, and do not exclude any field of endeavour. To
the extent possible in the relevant jurisdiction, these rights may be
exercised in all media and formats whether now known or created in the
future.

The rights granted cover, for example:

      a. Extraction and Re-utilisation of the whole or a Substantial part of
the Contents;

      b. Creation of Derivative Databases;

      c. Creation of Collective Databases;

      d. Creation of temporary or permanent reproductions by any means and
in any form, in whole or in part, including of any Derivative
Databases or as a part of Collective Databases; and

      e. Distribution, communication, display, lending, making available, or
performance to the public by any means and in any form, in whole or in
part, including of any Derivative Database or as a part of Collective
      Databases.

3.2 Compulsory license schemes. For the avoidance of doubt:

      a. Non-waivable compulsory license schemes. In those jurisdictions in
which the right to collect royalties through any statutory or
compulsory licensing scheme cannot be waived, the Licensor reserves
the exclusive right to collect such royalties for any exercise by You
of the rights granted under this License;

      b. Waivable compulsory license schemes. In those jurisdictions in
which the right to collect royalties through any statutory or
compulsory licensing scheme can be waived, the Licensor waives the
exclusive right to collect such royalties for any exercise by You of
the rights granted under this License; and,

      c. Voluntary license schemes. The Licensor waives the right to collect
royalties, whether individually or, in the event that the Licensor is
a member of a collecting society that administers voluntary licensing
schemes, via that society, from any exercise by You of the rights
granted under this License.

3.3 The right to release the Database under different terms, or to stop
distributing or making available the Database, is reserved. Note that
this Database may be multiple-licensed, and so You may have the choice
of using alternative licenses for this Database. Subject to Section
10.4, all other rights not expressly granted by Licensor are reserved.
4.0 Conditions of Use

4.1 The rights granted in Section 3 above are expressly made subject to
Your complying with the following conditions of use. These are important
conditions of this License, and if You fail to follow them, You will be
in material breach of its terms.

4.2 Notices. If You Publicly Convey this Database, any Derivative
Database, or the Database as part of a Collective Database, then You
must:

      a. Do so only under the terms of this License or another license
permitted under Section 4.4;

      b. Include a copy of this License (or, as applicable, a license
permitted under Section 4.4) or its Uniform Resource Identifier (URI)
with the Database or Derivative Database, including both in the
Database or Derivative Database and in any relevant documentation; and

      c. Keep intact any copyright or Database Right notices and notices
that refer to this License.

      d. If it is not possible to put the required notices in a particular
file due to its structure, then You must include the notices in a
location (such as a relevant directory) where users would be likely to
look for it.

4.3 Notice for using output (Contents). Creating and Using a Produced
Work does not require the notice in Section 4.2. However, if you
Publicly Use a Produced Work, You must include a notice associated with
the Produced Work reasonably calculated to make any Person that uses,
views, accesses, interacts with, or is otherwise exposed to the Produced
Work aware that Content was obtained from the Database, Derivative
Database, or the Database as part of a Collective Database, and that it
is available under this License.

      a. Example notice. The following text will satisfy notice under
Section 4.3:

    Contains information from DATABASE NAME, which is made available
    here under the Open Database License (ODbL).

DATABASE NAME should be replaced with the name of the Database and a
hyperlink to the URI of the Database. "Open Database License" should
contain a hyperlink to the URI of the text of this License. If
hyperlinks are not possible, You should include the plain text of the
required URI’s with the above notice.

4.4 Share alike.

      a. Any Derivative Database that You Publicly Use must be only under
the terms of:

           i. This License;

           ii. A later version of this License similar in spirit to this
License; or

           iii. A compatible license.

If You license the Derivative Database under one of the licenses
mentioned in (iii), You must comply with the terms of that license.

      b. For the avoidance of doubt, Extraction or Re-utilisation of the
whole or a Substantial part of the Contents INTO a new database is a
Derivative Database and must comply with Section 4.4.

      c. Derivative Databases and Produced Works. A Derivative Database is
Publicly Used and so must comply with Section 4.4. if a Produced Work
created from the Derivative Database is Publicly Used.

      d. Share Alike and additional Contents. For the avoidance of doubt,
You must not add Contents to Derivative Databases under Section 4.4 a
that are incompatible with the rights granted under this License.

      e. Compatible licenses. Licensors may authorise a proxy to determine
compatible licenses under Section 4.4 a iii. If they do so, the
authorised proxy’s public statement of acceptance of a compatible
license grants You permission to use the compatible license.

4.5 Limits of Share Alike. The requirements of Section 4.4 do not apply
in the following:

      a. For the avoidance of doubt, You are not required to license
Collective Databases under this License if You incorporate this
Database or a Derivative Database in the collection, but this License
still applies to this Database or a Derivative Database as a part of
the Collective Database;

      b. Using this Database, a Derivative Database, or this Database as
part of a Collective Database to create a Produced Work does not
create a Derivative Database for purposes of Section 4.4; and

      c. Use of a Derivative Database internally within an organisation is
not to the public and therefore does not fall under the requirements
of Section 4.4.

4.6 Access to Derivative Databases. If You Publicly Use a Derivative
Database or a Produced Work from a Derivative Database, You must also
offer to recipients of the Derivative Database or Produced Work a copy
in a machine readable form of:

      a. The entire Derivative Database; or

      b. A file containing all of the alterations made to the Database or
the method of making the alterations to the Database (such as an
algorithm), including any additional Contents, that make up all the
differences between the Database and the Derivative Database.

The Derivative Database (under a.) or alteration file (under b.) must be
available at no more than a reasonable production cost for physical
distributions and free of charge if distributed over the internet.

4.7 Technological measures and additional terms

      a. This License does not allow You to impose (except subject to
Section 4.7 b.) any terms or any technological measures on the
Database, a Derivative Database, or the whole or a Substantial part of
the Contents that alter or restrict the terms of this License, or any
rights granted under it, or have the effect or intent of restricting
the ability of any person to exercise those rights.

      b. Parallel distribution. You may impose terms or technological
measures on the Database, a Derivative Database, or the whole or a
Substantial part of the Contents (a "Restricted Database") in
contravention of Section 4.74 a. only if You also make a copy of the
Database or a Derivative Database available to the recipient of the
Restricted Database:

           i. That is available without additional fee;

           ii. That is available in a medium that does not alter or restrict
the terms of this License, or any rights granted under it, or have
the effect or intent of restricting the ability of any person to
exercise those rights (an "Unrestricted Database"); and

           iii. The Unrestricted Database is at least as accessible to the
recipient as a practical matter as the Restricted Database.

      c. For the avoidance of doubt, You may place this Database or a
Derivative Database in an authenticated environment, behind a
password, or within a similar access control scheme provided that You
do not alter or restrict the terms of this License or any rights
granted under it or have the effect or intent of restricting the
ability of any person to exercise those rights.

4.8 Licensing of others. You may not sublicense the Database. Each time
You communicate the Database, the whole or Substantial part of the
Contents, or any Derivative Database to anyone else in any way, the
Licensor offers to the recipient a license to the Database on the same
terms and conditions as this License. You are not responsible for
enforcing compliance by third parties with this License, but You may
enforce any rights that You have over a Derivative Database. You are
solely responsible for any modifications of a Derivative Database made
by You or another Person at Your direction. You may not impose any
further restrictions on the exercise of the rights granted or affirmed
under this License.
5.0 Moral rights

5.1 Moral rights. This section covers moral rights, including any rights
to be identified as the author of the Database or to object to treatment
that would otherwise prejudice the author’s honour and reputation, or
any other derogatory treatment:

      a. For jurisdictions allowing waiver of moral rights, Licensor waives
all moral rights that Licensor may have in the Database to the fullest
extent possible by the law of the relevant jurisdiction under Section
      10.4;

      b. If waiver of moral rights under Section 5.1 a in the relevant
jurisdiction is not possible, Licensor agrees not to assert any moral
rights over the Database and waives all claims in moral rights to the
fullest extent possible by the law of the relevant jurisdiction under
Section 10.4; and

      c. For jurisdictions not allowing waiver or an agreement not to assert
moral rights under Section 5.1 a and b, the author may retain their
moral rights over certain aspects of the Database.

Please note that some jurisdictions do not allow for the waiver of moral
rights, and so moral rights may still subsist over the Database in some
jurisdictions.
6.0 Fair dealing, Database exceptions, and other rights not affected

6.1 This License does not affect any rights that You or anyone else may
independently have under any applicable law to make any use of this
Database, including without limitation:

      a. Exceptions to the Database Right including: Extraction of Contents
from non-electronic Databases for private purposes, Extraction for
purposes of illustration for teaching or scientific research, and
Extraction or Re-utilisation for public security or an administrative
or judicial procedure.

      b. Fair dealing, fair use, or any other legally recognised limitation
or exception to infringement of copyright or other applicable laws.

6.2 This License does not affect any rights of lawful users to Extract
and Re-utilise insubstantial parts of the Contents, evaluated
quantitatively or qualitatively, for any purposes whatsoever, including
creating a Derivative Database (subject to other rights over the
Contents, see Section 2.4). The repeated and systematic Extraction or
Re-utilisation of insubstantial parts of the Contents may however amount
to the Extraction or Re-utilisation of a Substantial part of the
Contents.
7.0 Warranties and Disclaimer

7.1 The Database is licensed by the Licensor "as is" and without any
warranty of any kind, either express, implied, or arising by statute,
custom, course of dealing, or trade usage. Licensor specifically
disclaims any and all implied warranties or conditions of title,
non-infringement, accuracy or completeness, the presence or absence of
errors, fitness for a particular purpose, merchantability, or otherwise.
Some jurisdictions do not allow the exclusion of implied warranties, so
this exclusion may not apply to You.
8.0 Limitation of liability

8.1 Subject to any liability that may not be excluded or limited by law,
the Licensor is not liable for, and expressly excludes, all liability
for loss or damage however and whenever caused to anyone by any use
under this License, whether by You or by anyone else, and whether caused
by any fault on the part of the Licensor or not. This exclusion of
liability includes, but is not limited to, any special, incidental,
consequential, punitive, or exemplary damages such as loss of revenue,
data, anticipated profits, and lost business. This exclusion applies
even if the Licensor has been advised of the possibility of such
damages.

8.2 If liability may not be excluded by law, it is limited to actual and
direct financial loss to the extent it is caused by proved negligence on
the part of the Licensor.
9.0 Termination of Your rights under this License

9.1 Any breach by You of the terms and conditions of this License
automatically terminates this License with immediate effect and without
notice to You. For the avoidance of doubt, Persons who have received the
Database, the whole or a Substantial part of the Contents, Derivative
Databases, or the Database as part of a Collective Database from You
under this License will not have their licenses terminated provided
their use is in full compliance with this License or a license granted
under Section 4.8 of this License. Sections 1, 2, 7, 8, 9 and 10 will
survive any termination of this License.

9.2 If You are not in breach of the terms of this License, the Licensor
will not terminate Your rights under it.

9.3 Unless terminated under Section 9.1, this License is granted to You
for the duration of applicable rights in the Database.

9.4 Reinstatement of rights. If you cease any breach of the terms and
conditions of this License, then your full rights under this License
will be reinstated:

      a. Provisionally and subject to permanent termination until the 60th
day after cessation of breach;

      b. Permanently on the 60th day after cessation of breach unless
otherwise reasonably notified by the Licensor; or

      c. Permanently if reasonably notified by the Licensor of the
violation, this is the first time You have received notice of
violation of this License from the Licensor, and You cure the
violation prior to 30 days after your receipt of the notice.

Persons subject to permanent termination of rights are not eligible to
be a recipient and receive a license under Section 4.8.

9.5 Notwithstanding the above, Licensor reserves the right to release
the Database under different license terms or to stop distributing or
making available the Database. Releasing the Database under different
license terms or stopping the distribution of the Database will not
withdraw this License (or any other license that has been, or is
required to be, granted under the terms of this License), and this
License will continue in full force and effect unless terminated as
stated above.
10.0 General

10.1 If any provision of this License is held to be invalid or
unenforceable, that must not affect the validity or enforceability of
the remainder of the terms and conditions of this License and each
remaining provision of this License shall be valid and enforced to the
fullest extent permitted by law.

10.2 This License is the entire agreement between the parties with
respect to the rights granted here over the Database. It replaces any
earlier understandings, agreements or representations with respect to
the Database.

10.3 If You are in breach of the terms of this License, You will not be
entitled to rely on the terms of this License or to complain of any
breach by the Licensor.

10.4 Choice of law. This License takes effect in and will be governed by
the laws of the relevant jurisdiction in which the License terms are
sought to be enforced. If the standard suite of rights granted under
applicable copyright law and Database Rights in the relevant
jurisdiction includes additional rights not granted under this License,
these additional rights are granted in this License in order to meet the
terms of this License.', 'https://opendatacommons.org/licenses/odbl/1.0/index.html');

INSERT IGNORE INTO license(name, description, url) VALUES('CC BY-NC', "Creative Commons Attribution-NonCommercial 4.0 International Public License

By exercising the Licensed Rights (defined below), You accept and agree to be bound by the terms and conditions of this Creative Commons Attribution-NonCommercial 4.0 International Public License (Public License). To the extent this Public License may be interpreted as a contract, You are granted the Licensed Rights in consideration of Your acceptance of these terms and conditions, and the Licensor grants You such rights in consideration of benefits the Licensor receives from making the Licensed Material available under these terms and conditions.

Section 1 - Definitions.

    Adapted Material means material subject to Copyright and Similar Rights that is derived from or based upon the Licensed Material and in which the Licensed Material is translated, altered, arranged, transformed, or otherwise modified in a manner requiring permission under the Copyright and Similar Rights held by the Licensor. For purposes of this Public License, where the Licensed Material is a musical work, performance, or sound recording, Adapted Material is always produced where the Licensed Material is synched in timed relation with a moving image.
    Adapter's License means the license You apply to Your Copyright and Similar Rights in Your contributions to Adapted Material in accordance with the terms and conditions of this Public License.
    Copyright and Similar Rights means copyright and/or similar rights closely related to copyright including, without limitation, performance, broadcast, sound recording, and Sui Generis Database Rights, without regard to how the rights are labeled or categorized. For purposes of this Public License, the rights specified in Section 2(b)(1)-(2) are not Copyright and Similar Rights.
    Effective Technological Measures means those measures that, in the absence of proper authority, may not be circumvented under laws fulfilling obligations under Article 11 of the WIPO Copyright Treaty adopted on December 20, 1996, and/or similar international agreements.
    Exceptions and Limitations means fair use, fair dealing, and/or any other exception or limitation to Copyright and Similar Rights that applies to Your use of the Licensed Material.
    Licensed Material means the artistic or literary work, database, or other material to which the Licensor applied this Public License.
    Licensed Rights means the rights granted to You subject to the terms and conditions of this Public License, which are limited to all Copyright and Similar Rights that apply to Your use of the Licensed Material and that the Licensor has authority to license.
    Licensor means the individual(s) or entity(ies) granting rights under this Public License.
    NonCommercial means not primarily intended for or directed towards commercial advantage or monetary compensation. For purposes of this Public License, the exchange of the Licensed Material for other material subject to Copyright and Similar Rights by digital file-sharing or similar means is NonCommercial provided there is no payment of monetary compensation in connection with the exchange.
    Share means to provide material to the public by any means or process that requires permission under the Licensed Rights, such as reproduction, public display, public performance, distribution, dissemination, communication, or importation, and to make material available to the public including in ways that members of the public may access the material from a place and at a time individually chosen by them.
    Sui Generis Database Rights means rights other than copyright resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other essentially equivalent rights anywhere in the world.
    You means the individual or entity exercising the Licensed Rights under this Public License. Your has a corresponding meaning.

Section 2 - Scope.

    License grant.
        Subject to the terms and conditions of this Public License, the Licensor hereby grants You a worldwide, royalty-free, non-sublicensable, non-exclusive, irrevocable license to exercise the Licensed Rights in the Licensed Material to:
            reproduce and Share the Licensed Material, in whole or in part, for NonCommercial purposes only; and
            produce, reproduce, and Share Adapted Material for NonCommercial purposes only.
        Exceptions and Limitations. For the avoidance of doubt, where Exceptions and Limitations apply to Your use, this Public License does not apply, and You do not need to comply with its terms and conditions.
        Term. The term of this Public License is specified in Section 6(a).
        Media and formats; technical modifications allowed. The Licensor authorizes You to exercise the Licensed Rights in all media and formats whether now known or hereafter created, and to make technical modifications necessary to do so. The Licensor waives and/or agrees not to assert any right or authority to forbid You from making technical modifications necessary to exercise the Licensed Rights, including technical modifications necessary to circumvent Effective Technological Measures. For purposes of this Public License, simply making modifications authorized by this Section 2(a)(4) never produces Adapted Material.
        Downstream recipients.
            Offer from the Licensor - Licensed Material. Every recipient of the Licensed Material automatically receives an offer from the Licensor to exercise the Licensed Rights under the terms and conditions of this Public License.
            No downstream restrictions. You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, the Licensed Material if doing so restricts exercise of the Licensed Rights by any recipient of the Licensed Material.
        No endorsement. Nothing in this Public License constitutes or may be construed as permission to assert or imply that You are, or that Your use of the Licensed Material is, connected with, or sponsored, endorsed, or granted official status by, the Licensor or others designated to receive attribution as provided in Section 3(a)(1)(A)(i).

    Other rights.
        Moral rights, such as the right of integrity, are not licensed under this Public License, nor are publicity, privacy, and/or other similar personality rights; however, to the extent possible, the Licensor waives and/or agrees not to assert any such rights held by the Licensor to the limited extent necessary to allow You to exercise the Licensed Rights, but not otherwise.
        Patent and trademark rights are not licensed under this Public License.
        To the extent possible, the Licensor waives any right to collect royalties from You for the exercise of the Licensed Rights, whether directly or through a collecting society under any voluntary or waivable statutory or compulsory licensing scheme. In all other cases the Licensor expressly reserves any right to collect such royalties, including when the Licensed Material is used other than for NonCommercial purposes.

Section 3 - License Conditions.

Your exercise of the Licensed Rights is expressly made subject to the following conditions.

    Attribution.

        If You Share the Licensed Material (including in modified form), You must:
            retain the following if it is supplied by the Licensor with the Licensed Material:
                identification of the creator(s) of the Licensed Material and any others designated to receive attribution, in any reasonable manner requested by the Licensor (including by pseudonym if designated);
                a copyright notice;
                a notice that refers to this Public License;
                a notice that refers to the disclaimer of warranties;
                a URI or hyperlink to the Licensed Material to the extent reasonably practicable;
            indicate if You modified the Licensed Material and retain an indication of any previous modifications; and
            indicate the Licensed Material is licensed under this Public License, and include the text of, or the URI or hyperlink to, this Public License.
        You may satisfy the conditions in Section 3(a)(1) in any reasonable manner based on the medium, means, and context in which You Share the Licensed Material. For example, it may be reasonable to satisfy the conditions by providing a URI or hyperlink to a resource that includes the required information.
        If requested by the Licensor, You must remove any of the information required by Section 3(a)(1)(A) to the extent reasonably practicable.
        If You Share Adapted Material You produce, the Adapter`s License You apply must not prevent recipients of the Adapted Material from complying with this Public License.

Section 4 - Sui Generis Database Rights.

Where the Licensed Rights include Sui Generis Database Rights that apply to Your use of the Licensed Material:

    for the avoidance of doubt, Section 2(a)(1) grants You the right to extract, reuse, reproduce, and Share all or a substantial portion of the contents of the database for NonCommercial purposes only;
    if You include all or a substantial portion of the database contents in a database in which You have Sui Generis Database Rights, then the database in which You have Sui Generis Database Rights (but not its individual contents) is Adapted Material; and
    You must comply with the conditions in Section 3(a) if You Share all or a substantial portion of the contents of the database.

For the avoidance of doubt, this Section 4 supplements and does not replace Your obligations under this Public License where the Licensed Rights include other Copyright and Similar Rights.

Section 5 - Disclaimer of Warranties and Limitation of Liability.

    Unless otherwise separately undertaken by the Licensor, to the extent possible, the Licensor offers the Licensed Material as-is and as-available, and makes no representations or warranties of any kind concerning the Licensed Material, whether express, implied, statutory, or other. This includes, without limitation, warranties of title, merchantability, fitness for a particular purpose, non-infringement, absence of latent or other defects, accuracy, or the presence or absence of errors, whether or not known or discoverable. Where disclaimers of warranties are not allowed in full or in part, this disclaimer may not apply to You.
    To the extent possible, in no event will the Licensor be liable to You on any legal theory (including, without limitation, negligence) or otherwise for any direct, special, indirect, incidental, consequential, punitive, exemplary, or other losses, costs, expenses, or damages arising out of this Public License or use of the Licensed Material, even if the Licensor has been advised of the possibility of such losses, costs, expenses, or damages. Where a limitation of liability is not allowed in full or in part, this limitation may not apply to You.

    The disclaimer of warranties and limitation of liability provided above shall be interpreted in a manner that, to the extent possible, most closely approximates an absolute disclaimer and waiver of all liability.

Section 6 - Term and Termination.

    This Public License applies for the term of the Copyright and Similar Rights licensed here. However, if You fail to comply with this Public License, then Your rights under this Public License terminate automatically.

    Where Your right to use the Licensed Material has terminated under Section 6(a), it reinstates:
        automatically as of the date the violation is cured, provided it is cured within 30 days of Your discovery of the violation; or
        upon express reinstatement by the Licensor.
    For the avoidance of doubt, this Section 6(b) does not affect any right the Licensor may have to seek remedies for Your violations of this Public License.
    For the avoidance of doubt, the Licensor may also offer the Licensed Material under separate terms or conditions or stop distributing the Licensed Material at any time; however, doing so will not terminate this Public License.
    Sections 1, 5, 6, 7, and 8 survive termination of this Public License.

Section 7 - Other Terms and Conditions.

    The Licensor shall not be bound by any additional or different terms or conditions communicated by You unless expressly agreed.
    Any arrangements, understandings, or agreements regarding the Licensed Material not stated herein are separate from and independent of the terms and conditions of this Public License.

Section 8 - Interpretation.

    For the avoidance of doubt, this Public License does not, and shall not be interpreted to, reduce, limit, restrict, or impose conditions on any use of the Licensed Material that could lawfully be made without permission under this Public License.
    To the extent possible, if any provision of this Public License is deemed unenforceable, it shall be automatically reformed to the minimum extent necessary to make it enforceable. If the provision cannot be reformed, it shall be severed from this Public License without affecting the enforceability of the remaining terms and conditions.
    No term or condition of this Public License will be waived and no failure to comply consented to unless expressly agreed to by the Licensor.
    Nothing in this Public License constitutes or may be interpreted as a limitation upon, or waiver of, any privileges and immunities that apply to the Licensor or You, including from the legal processes of any jurisdiction or authority.
", "https://creativecommons.org/licenses/by-nc/4.0/legalcode");


INSERT IGNORE INTO license(name, description, url) VALUES('CC BY-ND','Creative Commons Attribution-NoDerivatives 4.0 International Public License

By exercising the Licensed Rights (defined below), You accept and agree to be bound by the terms and conditions of this Creative Commons Attribution-NoDerivatives 4.0 International Public License ("Public License"). To the extent this Public License may be interpreted as a contract, You are granted the Licensed Rights in consideration of Your acceptance of these terms and conditions, and the Licensor grants You such rights in consideration of benefits the Licensor receives from making the Licensed Material available under these terms and conditions.

Section 1 - Definitions.

    Adapted Material means material subject to Copyright and Similar Rights that is derived from or based upon the Licensed Material and in which the Licensed Material is translated, altered, arranged, transformed, or otherwise modified in a manner requiring permission under the Copyright and Similar Rights held by the Licensor. For purposes of this Public License, where the Licensed Material is a musical work, performance, or sound recording, Adapted Material is always produced where the Licensed Material is synched in timed relation with a moving image.
    Copyright and Similar Rights means copyright and/or similar rights closely related to copyright including, without limitation, performance, broadcast, sound recording, and Sui Generis Database Rights, without regard to how the rights are labeled or categorized. For purposes of this Public License, the rights specified in Section 2(b)(1)-(2) are not Copyright and Similar Rights.
    Effective Technological Measures means those measures that, in the absence of proper authority, may not be circumvented under laws fulfilling obligations under Article 11 of the WIPO Copyright Treaty adopted on December 20, 1996, and/or similar international agreements.
    Exceptions and Limitations means fair use, fair dealing, and/or any other exception or limitation to Copyright and Similar Rights that applies to Your use of the Licensed Material.
    Licensed Material means the artistic or literary work, database, or other material to which the Licensor applied this Public License.
    Licensed Rights means the rights granted to You subject to the terms and conditions of this Public License, which are limited to all Copyright and Similar Rights that apply to Your use of the Licensed Material and that the Licensor has authority to license.
    Licensor means the individual(s) or entity(ies) granting rights under this Public License.
    Share means to provide material to the public by any means or process that requires permission under the Licensed Rights, such as reproduction, public display, public performance, distribution, dissemination, communication, or importation, and to make material available to the public including in ways that members of the public may access the material from a place and at a time individually chosen by them.
    Sui Generis Database Rights means rights other than copyright resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other essentially equivalent rights anywhere in the world.
    You means the individual or entity exercising the Licensed Rights under this Public License. Your has a corresponding meaning.

Section 2 - Scope.

    License grant.
        Subject to the terms and conditions of this Public License, the Licensor hereby grants You a worldwide, royalty-free, non-sublicensable, non-exclusive, irrevocable license to exercise the Licensed Rights in the Licensed Material to:
            reproduce and Share the Licensed Material, in whole or in part; and
            produce and reproduce, but not Share, Adapted Material.
        Exceptions and Limitations. For the avoidance of doubt, where Exceptions and Limitations apply to Your use, this Public License does not apply, and You do not need to comply with its terms and conditions.
        Term. The term of this Public License is specified in Section 6(a).
        Media and formats; technical modifications allowed. The Licensor authorizes You to exercise the Licensed Rights in all media and formats whether now known or hereafter created, and to make technical modifications necessary to do so. The Licensor waives and/or agrees not to assert any right or authority to forbid You from making technical modifications necessary to exercise the Licensed Rights, including technical modifications necessary to circumvent Effective Technological Measures. For purposes of this Public License, simply making modifications authorized by this Section 2(a)(4) never produces Adapted Material.
        Downstream recipients.
            Offer from the Licensor - Licensed Material. Every recipient of the Licensed Material automatically receives an offer from the Licensor to exercise the Licensed Rights under the terms and conditions of this Public License.
            No downstream restrictions. You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, the Licensed Material if doing so restricts exercise of the Licensed Rights by any recipient of the Licensed Material.
        No endorsement. Nothing in this Public License constitutes or may be construed as permission to assert or imply that You are, or that Your use of the Licensed Material is, connected with, or sponsored, endorsed, or granted official status by, the Licensor or others designated to receive attribution as provided in Section 3(a)(1)(A)(i).

    Other rights.
        Moral rights, such as the right of integrity, are not licensed under this Public License, nor are publicity, privacy, and/or other similar personality rights; however, to the extent possible, the Licensor waives and/or agrees not to assert any such rights held by the Licensor to the limited extent necessary to allow You to exercise the Licensed Rights, but not otherwise.
        Patent and trademark rights are not licensed under this Public License.
        To the extent possible, the Licensor waives any right to collect royalties from You for the exercise of the Licensed Rights, whether directly or through a collecting society under any voluntary or waivable statutory or compulsory licensing scheme. In all other cases the Licensor expressly reserves any right to collect such royalties.

Section 3 - License Conditions.

Your exercise of the Licensed Rights is expressly made subject to the following conditions.

    Attribution.

        If You Share the Licensed Material, You must:
            retain the following if it is supplied by the Licensor with the Licensed Material:
                identification of the creator(s) of the Licensed Material and any others designated to receive attribution, in any reasonable manner requested by the Licensor (including by pseudonym if designated);
                a copyright notice;
                a notice that refers to this Public License;
                a notice that refers to the disclaimer of warranties;
                a URI or hyperlink to the Licensed Material to the extent reasonably practicable;
            indicate if You modified the Licensed Material and retain an indication of any previous modifications; and
            indicate the Licensed Material is licensed under this Public License, and include the text of, or the URI or hyperlink to, this Public License.
        For the avoidance of doubt, You do not have permission under this Public License to Share Adapted Material.
        You may satisfy the conditions in Section 3(a)(1) in any reasonable manner based on the medium, means, and context in which You Share the Licensed Material. For example, it may be reasonable to satisfy the conditions by providing a URI or hyperlink to a resource that includes the required information.
        If requested by the Licensor, You must remove any of the information required by Section 3(a)(1)(A) to the extent reasonably practicable.

Section 4 - Sui Generis Database Rights.

Where the Licensed Rights include Sui Generis Database Rights that apply to Your use of the Licensed Material:

    for the avoidance of doubt, Section 2(a)(1) grants You the right to extract, reuse, reproduce, and Share all or a substantial portion of the contents of the database, provided You do not Share Adapted Material;
    if You include all or a substantial portion of the database contents in a database in which You have Sui Generis Database Rights, then the database in which You have Sui Generis Database Rights (but not its individual contents) is Adapted Material; and
    You must comply with the conditions in Section 3(a) if You Share all or a substantial portion of the contents of the database.

For the avoidance of doubt, this Section 4 supplements and does not replace Your obligations under this Public License where the Licensed Rights include other Copyright and Similar Rights.

Section 5 - Disclaimer of Warranties and Limitation of Liability.

    Unless otherwise separately undertaken by the Licensor, to the extent possible, the Licensor offers the Licensed Material as-is and as-available, and makes no representations or warranties of any kind concerning the Licensed Material, whether express, implied, statutory, or other. This includes, without limitation, warranties of title, merchantability, fitness for a particular purpose, non-infringement, absence of latent or other defects, accuracy, or the presence or absence of errors, whether or not known or discoverable. Where disclaimers of warranties are not allowed in full or in part, this disclaimer may not apply to You.
    To the extent possible, in no event will the Licensor be liable to You on any legal theory (including, without limitation, negligence) or otherwise for any direct, special, indirect, incidental, consequential, punitive, exemplary, or other losses, costs, expenses, or damages arising out of this Public License or use of the Licensed Material, even if the Licensor has been advised of the possibility of such losses, costs, expenses, or damages. Where a limitation of liability is not allowed in full or in part, this limitation may not apply to You.

    The disclaimer of warranties and limitation of liability provided above shall be interpreted in a manner that, to the extent possible, most closely approximates an absolute disclaimer and waiver of all liability.

Section 6 - Term and Termination.

    This Public License applies for the term of the Copyright and Similar Rights licensed here. However, if You fail to comply with this Public License, then Your rights under this Public License terminate automatically.

    Where Your right to use the Licensed Material has terminated under Section 6(a), it reinstates:
        automatically as of the date the violation is cured, provided it is cured within 30 days of Your discovery of the violation; or
        upon express reinstatement by the Licensor.
    For the avoidance of doubt, this Section 6(b) does not affect any right the Licensor may have to seek remedies for Your violations of this Public License.
    For the avoidance of doubt, the Licensor may also offer the Licensed Material under separate terms or conditions or stop distributing the Licensed Material at any time; however, doing so will not terminate this Public License.
    Sections 1, 5, 6, 7, and 8 survive termination of this Public License.

Section 7 - Other Terms and Conditions.

    The Licensor shall not be bound by any additional or different terms or conditions communicated by You unless expressly agreed.
    Any arrangements, understandings, or agreements regarding the Licensed Material not stated herein are separate from and independent of the terms and conditions of this Public License.

Section 8 - Interpretation.

    For the avoidance of doubt, this Public License does not, and shall not be interpreted to, reduce, limit, restrict, or impose conditions on any use of the Licensed Material that could lawfully be made without permission under this Public License.
    To the extent possible, if any provision of this Public License is deemed unenforceable, it shall be automatically reformed to the minimum extent necessary to make it enforceable. If the provision cannot be reformed, it shall be severed from this Public License without affecting the enforceability of the remaining terms and conditions.
    No term or condition of this Public License will be waived and no failure to comply consented to unless expressly agreed to by the Licensor.
    Nothing in this Public License constitutes or may be interpreted as a limitation upon, or waiver of, any privileges and immunities that apply to the Licensor or You, including from the legal processes of any jurisdiction or authority.
', 'https://creativecommons.org/licenses/by-nd/4.0/legalcode');

INSERT IGNORE INTO license(name, description, url) VALUES('CC BY-NC-SA',"Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License

By exercising the Licensed Rights (defined below), You accept and agree to be bound by the terms and conditions of this Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License (Public License). To the extent this Public License may be interpreted as a contract, You are granted the Licensed Rights in consideration of Your acceptance of these terms and conditions, and the Licensor grants You such rights in consideration of benefits the Licensor receives from making the Licensed Material available under these terms and conditions.

Section 1 - Definitions.

    Adapted Material means material subject to Copyright and Similar Rights that is derived from or based upon the Licensed Material and in which the Licensed Material is translated, altered, arranged, transformed, or otherwise modified in a manner requiring permission under the Copyright and Similar Rights held by the Licensor. For purposes of this Public License, where the Licensed Material is a musical work, performance, or sound recording, Adapted Material is always produced where the Licensed Material is synched in timed relation with a moving image.
    Adapter's License means the license You apply to Your Copyright and Similar Rights in Your contributions to Adapted Material in accordance with the terms and conditions of this Public License.
    BY-NC-SA Compatible License means a license listed at creativecommons.org/compatiblelicenses, approved by Creative Commons as essentially the equivalent of this Public License.
    Copyright and Similar Rights means copyright and/or similar rights closely related to copyright including, without limitation, performance, broadcast, sound recording, and Sui Generis Database Rights, without regard to how the rights are labeled or categorized. For purposes of this Public License, the rights specified in Section 2(b)(1)-(2) are not Copyright and Similar Rights.
    Effective Technological Measures means those measures that, in the absence of proper authority, may not be circumvented under laws fulfilling obligations under Article 11 of the WIPO Copyright Treaty adopted on December 20, 1996, and/or similar international agreements.
    Exceptions and Limitations means fair use, fair dealing, and/or any other exception or limitation to Copyright and Similar Rights that applies to Your use of the Licensed Material.
    License Elements means the license attributes listed in the name of a Creative Commons Public License. The License Elements of this Public License are Attribution, NonCommercial, and ShareAlike.
    Licensed Material means the artistic or literary work, database, or other material to which the Licensor applied this Public License.
    Licensed Rights means the rights granted to You subject to the terms and conditions of this Public License, which are limited to all Copyright and Similar Rights that apply to Your use of the Licensed Material and that the Licensor has authority to license.
    Licensor means the individual(s) or entity(ies) granting rights under this Public License.
    NonCommercial means not primarily intended for or directed towards commercial advantage or monetary compensation. For purposes of this Public License, the exchange of the Licensed Material for other material subject to Copyright and Similar Rights by digital file-sharing or similar means is NonCommercial provided there is no payment of monetary compensation in connection with the exchange.
    Share means to provide material to the public by any means or process that requires permission under the Licensed Rights, such as reproduction, public display, public performance, distribution, dissemination, communication, or importation, and to make material available to the public including in ways that members of the public may access the material from a place and at a time individually chosen by them.
    Sui Generis Database Rights means rights other than copyright resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other essentially equivalent rights anywhere in the world.
    You means the individual or entity exercising the Licensed Rights under this Public License. Your has a corresponding meaning.

Section 2 - Scope.

    License grant.
        Subject to the terms and conditions of this Public License, the Licensor hereby grants You a worldwide, royalty-free, non-sublicensable, non-exclusive, irrevocable license to exercise the Licensed Rights in the Licensed Material to:
            reproduce and Share the Licensed Material, in whole or in part, for NonCommercial purposes only; and
            produce, reproduce, and Share Adapted Material for NonCommercial purposes only.
        Exceptions and Limitations. For the avoidance of doubt, where Exceptions and Limitations apply to Your use, this Public License does not apply, and You do not need to comply with its terms and conditions.
        Term. The term of this Public License is specified in Section 6(a).
        Media and formats; technical modifications allowed. The Licensor authorizes You to exercise the Licensed Rights in all media and formats whether now known or hereafter created, and to make technical modifications necessary to do so. The Licensor waives and/or agrees not to assert any right or authority to forbid You from making technical modifications necessary to exercise the Licensed Rights, including technical modifications necessary to circumvent Effective Technological Measures. For purposes of this Public License, simply making modifications authorized by this Section 2(a)(4) never produces Adapted Material.
        Downstream recipients.
            Offer from the Licensor - Licensed Material. Every recipient of the Licensed Material automatically receives an offer from the Licensor to exercise the Licensed Rights under the terms and conditions of this Public License.
            Additional offer from the Licensor - Adapted Material. Every recipient of Adapted Material from You automatically receives an offer from the Licensor to exercise the Licensed Rights in the Adapted Material under the conditions of the Adapter’s License You apply.
            No downstream restrictions. You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, the Licensed Material if doing so restricts exercise of the Licensed Rights by any recipient of the Licensed Material.
        No endorsement. Nothing in this Public License constitutes or may be construed as permission to assert or imply that You are, or that Your use of the Licensed Material is, connected with, or sponsored, endorsed, or granted official status by, the Licensor or others designated to receive attribution as provided in Section 3(a)(1)(A)(i).

    Other rights.
        Moral rights, such as the right of integrity, are not licensed under this Public License, nor are publicity, privacy, and/or other similar personality rights; however, to the extent possible, the Licensor waives and/or agrees not to assert any such rights held by the Licensor to the limited extent necessary to allow You to exercise the Licensed Rights, but not otherwise.
        Patent and trademark rights are not licensed under this Public License.
        To the extent possible, the Licensor waives any right to collect royalties from You for the exercise of the Licensed Rights, whether directly or through a collecting society under any voluntary or waivable statutory or compulsory licensing scheme. In all other cases the Licensor expressly reserves any right to collect such royalties, including when the Licensed Material is used other than for NonCommercial purposes.

Section 3 - License Conditions.

Your exercise of the Licensed Rights is expressly made subject to the following conditions.

    Attribution.

        If You Share the Licensed Material (including in modified form), You must:
            retain the following if it is supplied by the Licensor with the Licensed Material:
                identification of the creator(s) of the Licensed Material and any others designated to receive attribution, in any reasonable manner requested by the Licensor (including by pseudonym if designated);
                a copyright notice;
                a notice that refers to this Public License;
                a notice that refers to the disclaimer of warranties;
                a URI or hyperlink to the Licensed Material to the extent reasonably practicable;
            indicate if You modified the Licensed Material and retain an indication of any previous modifications; and
            indicate the Licensed Material is licensed under this Public License, and include the text of, or the URI or hyperlink to, this Public License.
        You may satisfy the conditions in Section 3(a)(1) in any reasonable manner based on the medium, means, and context in which You Share the Licensed Material. For example, it may be reasonable to satisfy the conditions by providing a URI or hyperlink to a resource that includes the required information.
        If requested by the Licensor, You must remove any of the information required by Section 3(a)(1)(A) to the extent reasonably practicable.
    ShareAlike.

    In addition to the conditions in Section 3(a), if You Share Adapted Material You produce, the following conditions also apply.
        The Adapter’s License You apply must be a Creative Commons license with the same License Elements, this version or later, or a BY-NC-SA Compatible License.
        You must include the text of, or the URI or hyperlink to, the Adapter's License You apply. You may satisfy this condition in any reasonable manner based on the medium, means, and context in which You Share Adapted Material.
        You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, Adapted Material that restrict exercise of the rights granted under the Adapter's License You apply.

Section 4 - Sui Generis Database Rights.

Where the Licensed Rights include Sui Generis Database Rights that apply to Your use of the Licensed Material:

    for the avoidance of doubt, Section 2(a)(1) grants You the right to extract, reuse, reproduce, and Share all or a substantial portion of the contents of the database for NonCommercial purposes only;
    if You include all or a substantial portion of the database contents in a database in which You have Sui Generis Database Rights, then the database in which You have Sui Generis Database Rights (but not its individual contents) is Adapted Material, including for purposes of Section 3(b); and
    You must comply with the conditions in Section 3(a) if You Share all or a substantial portion of the contents of the database.

For the avoidance of doubt, this Section 4 supplements and does not replace Your obligations under this Public License where the Licensed Rights include other Copyright and Similar Rights.

Section 5 - Disclaimer of Warranties and Limitation of Liability.

    Unless otherwise separately undertaken by the Licensor, to the extent possible, the Licensor offers the Licensed Material as-is and as-available, and makes no representations or warranties of any kind concerning the Licensed Material, whether express, implied, statutory, or other. This includes, without limitation, warranties of title, merchantability, fitness for a particular purpose, non-infringement, absence of latent or other defects, accuracy, or the presence or absence of errors, whether or not known or discoverable. Where disclaimers of warranties are not allowed in full or in part, this disclaimer may not apply to You.
    To the extent possible, in no event will the Licensor be liable to You on any legal theory (including, without limitation, negligence) or otherwise for any direct, special, indirect, incidental, consequential, punitive, exemplary, or other losses, costs, expenses, or damages arising out of this Public License or use of the Licensed Material, even if the Licensor has been advised of the possibility of such losses, costs, expenses, or damages. Where a limitation of liability is not allowed in full or in part, this limitation may not apply to You.

    The disclaimer of warranties and limitation of liability provided above shall be interpreted in a manner that, to the extent possible, most closely approximates an absolute disclaimer and waiver of all liability.

Section 6 - Term and Termination.

    This Public License applies for the term of the Copyright and Similar Rights licensed here. However, if You fail to comply with this Public License, then Your rights under this Public License terminate automatically.

    Where Your right to use the Licensed Material has terminated under Section 6(a), it reinstates:
        automatically as of the date the violation is cured, provided it is cured within 30 days of Your discovery of the violation; or
        upon express reinstatement by the Licensor.
    For the avoidance of doubt, this Section 6(b) does not affect any right the Licensor may have to seek remedies for Your violations of this Public License.
    For the avoidance of doubt, the Licensor may also offer the Licensed Material under separate terms or conditions or stop distributing the Licensed Material at any time; however, doing so will not terminate this Public License.
    Sections 1, 5, 6, 7, and 8 survive termination of this Public License.

Section 7 - Other Terms and Conditions.

    The Licensor shall not be bound by any additional or different terms or conditions communicated by You unless expressly agreed.
    Any arrangements, understandings, or agreements regarding the Licensed Material not stated herein are separate from and independent of the terms and conditions of this Public License.

Section 8 - Interpretation.

    For the avoidance of doubt, this Public License does not, and shall not be interpreted to, reduce, limit, restrict, or impose conditions on any use of the Licensed Material that could lawfully be made without permission under this Public License.
    To the extent possible, if any provision of this Public License is deemed unenforceable, it shall be automatically reformed to the minimum extent necessary to make it enforceable. If the provision cannot be reformed, it shall be severed from this Public License without affecting the enforceability of the remaining terms and conditions.
    No term or condition of this Public License will be waived and no failure to comply consented to unless expressly agreed to by the Licensor.
    Nothing in this Public License constitutes or may be interpreted as a limitation upon, or waiver of, any privileges and immunities that apply to the Licensor or You, including from the legal processes of any jurisdiction or authority.
",'https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode');

INSERT IGNORE INTO license(name, description, url) VALUES("CC BY-NC-ND", "Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License

By exercising the Licensed Rights (defined below), You accept and agree to be bound by the terms and conditions of this Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License (Public License). To the extent this Public License may be interpreted as a contract, You are granted the Licensed Rights in consideration of Your acceptance of these terms and conditions, and the Licensor grants You such rights in consideration of benefits the Licensor receives from making the Licensed Material available under these terms and conditions.

Section 1 - Definitions.

    Adapted Material means material subject to Copyright and Similar Rights that is derived from or based upon the Licensed Material and in which the Licensed Material is translated, altered, arranged, transformed, or otherwise modified in a manner requiring permission under the Copyright and Similar Rights held by the Licensor. For purposes of this Public License, where the Licensed Material is a musical work, performance, or sound recording, Adapted Material is always produced where the Licensed Material is synched in timed relation with a moving image.
    Copyright and Similar Rights means copyright and/or similar rights closely related to copyright including, without limitation, performance, broadcast, sound recording, and Sui Generis Database Rights, without regard to how the rights are labeled or categorized. For purposes of this Public License, the rights specified in Section 2(b)(1)-(2) are not Copyright and Similar Rights.
    Effective Technological Measures means those measures that, in the absence of proper authority, may not be circumvented under laws fulfilling obligations under Article 11 of the WIPO Copyright Treaty adopted on December 20, 1996, and/or similar international agreements.
    Exceptions and Limitations means fair use, fair dealing, and/or any other exception or limitation to Copyright and Similar Rights that applies to Your use of the Licensed Material.
    Licensed Material means the artistic or literary work, database, or other material to which the Licensor applied this Public License.
    Licensed Rights means the rights granted to You subject to the terms and conditions of this Public License, which are limited to all Copyright and Similar Rights that apply to Your use of the Licensed Material and that the Licensor has authority to license.
    Licensor means the individual(s) or entity(ies) granting rights under this Public License.
    NonCommercial means not primarily intended for or directed towards commercial advantage or monetary compensation. For purposes of this Public License, the exchange of the Licensed Material for other material subject to Copyright and Similar Rights by digital file-sharing or similar means is NonCommercial provided there is no payment of monetary compensation in connection with the exchange.
    Share means to provide material to the public by any means or process that requires permission under the Licensed Rights, such as reproduction, public display, public performance, distribution, dissemination, communication, or importation, and to make material available to the public including in ways that members of the public may access the material from a place and at a time individually chosen by them.
    Sui Generis Database Rights means rights other than copyright resulting from Directive 96/9/EC of the European Parliament and of the Council of 11 March 1996 on the legal protection of databases, as amended and/or succeeded, as well as other essentially equivalent rights anywhere in the world.
    You means the individual or entity exercising the Licensed Rights under this Public License. Your has a corresponding meaning.

Section 2 - Scope.

    License grant.
        Subject to the terms and conditions of this Public License, the Licensor hereby grants You a worldwide, royalty-free, non-sublicensable, non-exclusive, irrevocable license to exercise the Licensed Rights in the Licensed Material to:
            reproduce and Share the Licensed Material, in whole or in part, for NonCommercial purposes only; and
            produce and reproduce, but not Share, Adapted Material for NonCommercial purposes only.
        Exceptions and Limitations. For the avoidance of doubt, where Exceptions and Limitations apply to Your use, this Public License does not apply, and You do not need to comply with its terms and conditions.
        Term. The term of this Public License is specified in Section 6(a).
        Media and formats; technical modifications allowed. The Licensor authorizes You to exercise the Licensed Rights in all media and formats whether now known or hereafter created, and to make technical modifications necessary to do so. The Licensor waives and/or agrees not to assert any right or authority to forbid You from making technical modifications necessary to exercise the Licensed Rights, including technical modifications necessary to circumvent Effective Technological Measures. For purposes of this Public License, simply making modifications authorized by this Section 2(a)(4) never produces Adapted Material.
        Downstream recipients.
            Offer from the Licensor - Licensed Material. Every recipient of the Licensed Material automatically receives an offer from the Licensor to exercise the Licensed Rights under the terms and conditions of this Public License.
            No downstream restrictions. You may not offer or impose any additional or different terms or conditions on, or apply any Effective Technological Measures to, the Licensed Material if doing so restricts exercise of the Licensed Rights by any recipient of the Licensed Material.
        No endorsement. Nothing in this Public License constitutes or may be construed as permission to assert or imply that You are, or that Your use of the Licensed Material is, connected with, or sponsored, endorsed, or granted official status by, the Licensor or others designated to receive attribution as provided in Section 3(a)(1)(A)(i).

    Other rights.
        Moral rights, such as the right of integrity, are not licensed under this Public License, nor are publicity, privacy, and/or other similar personality rights; however, to the extent possible, the Licensor waives and/or agrees not to assert any such rights held by the Licensor to the limited extent necessary to allow You to exercise the Licensed Rights, but not otherwise.
        Patent and trademark rights are not licensed under this Public License.
        To the extent possible, the Licensor waives any right to collect royalties from You for the exercise of the Licensed Rights, whether directly or through a collecting society under any voluntary or waivable statutory or compulsory licensing scheme. In all other cases the Licensor expressly reserves any right to collect such royalties, including when the Licensed Material is used other than for NonCommercial purposes.

Section 3 - License Conditions.

Your exercise of the Licensed Rights is expressly made subject to the following conditions.

    Attribution.

        If You Share the Licensed Material, You must:
            retain the following if it is supplied by the Licensor with the Licensed Material:
                identification of the creator(s) of the Licensed Material and any others designated to receive attribution, in any reasonable manner requested by the Licensor (including by pseudonym if designated);
                a copyright notice;
                a notice that refers to this Public License;
                a notice that refers to the disclaimer of warranties;
                a URI or hyperlink to the Licensed Material to the extent reasonably practicable;
            indicate if You modified the Licensed Material and retain an indication of any previous modifications; and
            indicate the Licensed Material is licensed under this Public License, and include the text of, or the URI or hyperlink to, this Public License.
        For the avoidance of doubt, You do not have permission under this Public License to Share Adapted Material.
        You may satisfy the conditions in Section 3(a)(1) in any reasonable manner based on the medium, means, and context in which You Share the Licensed Material. For example, it may be reasonable to satisfy the conditions by providing a URI or hyperlink to a resource that includes the required information.
        If requested by the Licensor, You must remove any of the information required by Section 3(a)(1)(A) to the extent reasonably practicable.

Section 4 - Sui Generis Database Rights.

Where the Licensed Rights include Sui Generis Database Rights that apply to Your use of the Licensed Material:

    for the avoidance of doubt, Section 2(a)(1) grants You the right to extract, reuse, reproduce, and Share all or a substantial portion of the contents of the database for NonCommercial purposes only and provided You do not Share Adapted Material;
    if You include all or a substantial portion of the database contents in a database in which You have Sui Generis Database Rights, then the database in which You have Sui Generis Database Rights (but not its individual contents) is Adapted Material; and
    You must comply with the conditions in Section 3(a) if You Share all or a substantial portion of the contents of the database.

For the avoidance of doubt, this Section 4 supplements and does not replace Your obligations under this Public License where the Licensed Rights include other Copyright and Similar Rights.

Section 5 - Disclaimer of Warranties and Limitation of Liability.

    Unless otherwise separately undertaken by the Licensor, to the extent possible, the Licensor offers the Licensed Material as-is and as-available, and makes no representations or warranties of any kind concerning the Licensed Material, whether express, implied, statutory, or other. This includes, without limitation, warranties of title, merchantability, fitness for a particular purpose, non-infringement, absence of latent or other defects, accuracy, or the presence or absence of errors, whether or not known or discoverable. Where disclaimers of warranties are not allowed in full or in part, this disclaimer may not apply to You.
    To the extent possible, in no event will the Licensor be liable to You on any legal theory (including, without limitation, negligence) or otherwise for any direct, special, indirect, incidental, consequential, punitive, exemplary, or other losses, costs, expenses, or damages arising out of this Public License or use of the Licensed Material, even if the Licensor has been advised of the possibility of such losses, costs, expenses, or damages. Where a limitation of liability is not allowed in full or in part, this limitation may not apply to You.

    The disclaimer of warranties and limitation of liability provided above shall be interpreted in a manner that, to the extent possible, most closely approximates an absolute disclaimer and waiver of all liability.

Section 6 - Term and Termination.

    This Public License applies for the term of the Copyright and Similar Rights licensed here. However, if You fail to comply with this Public License, then Your rights under this Public License terminate automatically.

    Where Your right to use the Licensed Material has terminated under Section 6(a), it reinstates:
        automatically as of the date the violation is cured, provided it is cured within 30 days of Your discovery of the violation; or
        upon express reinstatement by the Licensor.
    For the avoidance of doubt, this Section 6(b) does not affect any right the Licensor may have to seek remedies for Your violations of this Public License.
    For the avoidance of doubt, the Licensor may also offer the Licensed Material under separate terms or conditions or stop distributing the Licensed Material at any time; however, doing so will not terminate this Public License.
    Sections 1, 5, 6, 7, and 8 survive termination of this Public License.

Section 7 - Other Terms and Conditions.

    The Licensor shall not be bound by any additional or different terms or conditions communicated by You unless expressly agreed.
    Any arrangements, understandings, or agreements regarding the Licensed Material not stated herein are separate from and independent of the terms and conditions of this Public License.

Section 8 - Interpretation.

    For the avoidance of doubt, this Public License does not, and shall not be interpreted to, reduce, limit, restrict, or impose conditions on any use of the Licensed Material that could lawfully be made without permission under this Public License.
    To the extent possible, if any provision of this Public License is deemed unenforceable, it shall be automatically reformed to the minimum extent necessary to make it enforceable. If the provision cannot be reformed, it shall be severed from this Public License without affecting the enforceability of the remaining terms and conditions.
    No term or condition of this Public License will be waived and no failure to comply consented to unless expressly agreed to by the Licensor.
    Nothing in this Public License constitutes or may be interpreted as a limitation upon, or waiver of, any privileges and immunities that apply to the Licensor or You, including from the legal processes of any jurisdiction or authority.
","https://creativecommons.org/licenses/by-nc-nd/4.0/legalcode" );


INSERT IGNORE INTO license(name, description, url) VALUES('TREC License', 'Agreement for use of the
2007 TREC Public Spam Corpus

By clicking "I accept this agreement" below, in consideration of the right to download and use the information designated as the 2007 TREC Public Spam Corpus,
I (hereafter referred to as "Downloader") agree to be subject to the following understandings, terms and conditions. These understandings, terms and conditions apply equally to all or to part of the information.

Permitted Uses

    The information may only be used for research and development of email, natural-language-processing, information-retrieval or document-understanding systems.
    Summaries, analyses and interpretations of the linguistic properties of the information may be derived and published, provided it is not possible to reconstruct the information from these summaries.
    Small excerpts of the information may be displayed to others or published in a scientific or technical context, solely for the purpose of describing the research and development and related issues. Any such use shall not infringe on the rights of any third party including, but not limited to, the authors and publishers of the excerpts.
    The disclosure of personal information that may be contained in or derived from this corpus is prohibited.

Copyright

    The copyright holders retain ownership and reserve all rights pertaining to the use and distribution of the information.
    Except as specifically permitted above and as necessary to use and maintain the integrity of the information on computers used by Downloader; the display, reproduction, transmission, distribution or publication of the information is prohibited. Violations of the copyright restrictions on the information may result in legal liability.

Assumption of Risk

    This corpus may contain  computer viruses, fraudulent solicitations, and other damaging material.  The material should not be viewed with an ordinary file browser, email client, or web client.
    Virus checking software may corrupt the data in this corpus, or prevent it from being copied or transmitted.
    Downloader asserts that he or she is aware of the risks posed by the data contained in this corpus, will exercise due caution, and will hold harmless the authors and distributors of the corpus for any damage it may cause.
    Downloader acknowledges that this corpus is made available without warranty of any kind.', 'https://plg.uwaterloo.ca/~gvcormac/treccorpus07/');
