--ALTER TABLE OBJECT                ADD CONSTRAINT PK_OBJECT_AOID                    PRIMARY KEY(AOID);
ALTER TABLE HOUSE                 ADD CONSTRAINT PK_HOUSE_HOUSEID                  PRIMARY KEY(HOUSEID);
ALTER TABLE HOUSEINTERVAL         ADD CONSTRAINT PK_HOUSEINTERVAL_HOUSEINTID       PRIMARY KEY(HOUSEINTID);
ALTER TABLE LANDMARK              ADD CONSTRAINT PK_LANDMARK_LANDID                PRIMARY KEY(LANDID);
ALTER TABLE NORMATIVEDOCUMENT     ADD CONSTRAINT PK_NORMATIVEDOCUMENT_NORMDOCID    PRIMARY KEY(NORMDOCID);
ALTER TABLE NORMATIVEDOCUMENTTYPE ADD CONSTRAINT PK_NORMATIVEDOCUMENTTYPE_NDTYPEID PRIMARY KEY(NDTYPEID);
ALTER TABLE ADDRESSOBJECTTYPE     ADD CONSTRAINT PK_ADDRESSOBJECTTYPE_KODTST       PRIMARY KEY(KOD_T_ST);
ALTER TABLE CURRENTSTATUS         ADD CONSTRAINT PK_CURRENTSTATUS_CURENTSTID       PRIMARY KEY(CURENTSTID);
ALTER TABLE ACTUALSTATUS          ADD CONSTRAINT PK_ACTUALSTATUS_ACTSTATID         PRIMARY KEY(ACTSTATID);
ALTER TABLE OPERATIONSTATUS       ADD CONSTRAINT PK_OPERATIONSTATUS_OPERSTATID     PRIMARY KEY(OPERSTATID);
ALTER TABLE CENTERSTATUS          ADD CONSTRAINT PK_CENTERSTATUS_CENTERSTID        PRIMARY KEY(CENTERSTID);
ALTER TABLE INTERVALSTATUS        ADD CONSTRAINT PK_INTERVALSTATUS_INTVSTATID      PRIMARY KEY(INTVSTATID);
ALTER TABLE HOUSESTATESTATUS      ADD CONSTRAINT PK_HOUSESTATESTATUS_HOUSESTID     PRIMARY KEY(HOUSESTID);
ALTER TABLE ESTATESTATUS          ADD CONSTRAINT PK_ESTATESTATUS_ESTSTATID         PRIMARY KEY(ESTSTATID);
ALTER TABLE STRUCTURESTATUS       ADD CONSTRAINT PK_STRUCTURESTATUS_STRSTATID      PRIMARY KEY(STRSTATID);

CREATE INDEX IDX_OBJECT_AOGUID              ON OBJECT(AOGUID);
CREATE INDEX IDX_OBJECT_FORMALNAME          ON OBJECT(FORMALNAME);
CREATE INDEX IDX_OBJECT_REGIONCODE          ON OBJECT(REGIONCODE);
CREATE INDEX IDX_OBJECT_POSTALCODE          ON OBJECT(POSTALCODE);
CREATE INDEX IDX_OBJECT_SHORTNAME           ON OBJECT(SHORTNAME);
CREATE INDEX IDX_OBJECT_AOLEVEL             ON OBJECT(AOLEVEL);
CREATE INDEX IDX_OBJECT_PARENTGUID          ON OBJECT(PARENTGUID);
CREATE INDEX IDX_OBJECT_NORMDOC             ON OBJECT(NORMDOC);
CREATE INDEX IDX_HOUSE_POSTALCODE           ON HOUSE(POSTALCODE);
CREATE INDEX IDX_HOUSE_HOUSENUM             ON HOUSE(HOUSENUM);
CREATE INDEX IDX_HOUSE_NORMDOC              ON HOUSE(NORMDOC);
CREATE INDEX IDX_HOUSEINTERVAL_POSTALCODE   ON HOUSEINTERVAL(POSTALCODE);
CREATE INDEX IDX_HOUSEINTERVAL_INTSTART     ON HOUSEINTERVAL(INTSTART);
CREATE INDEX IDX_HOUSEINTERVAL_INTEND       ON HOUSEINTERVAL(INTEND);
CREATE INDEX IDX_HOUSEINTERVAL_NORMDOC      ON HOUSEINTERVAL(NORMDOC);
CREATE INDEX IDX_LANDMARK_LOCATION          ON LANDMARK(LOCATION);
CREATE INDEX IDX_LANDMARK_POSTALCODE        ON LANDMARK(POSTALCODE);
CREATE INDEX IDX_LANDMARK_NORMDOC           ON LANDMARK(NORMDOC);
CREATE INDEX IDX_NORMATIVEDOCUMENT_DOCNUM   ON NORMATIVEDOCUMENT(DOCNUM);
CREATE INDEX IDX_NORMATIVEDOCUMENTTYPE_NAME ON NORMATIVEDOCUMENTTYPE(NAME);
CREATE INDEX IDX_ADDRESSOBJECTTYPE_SOCRNAME ON ADDRESSOBJECTTYPE(SOCRNAME);
CREATE INDEX IDX_ADDRESSOBJECTTYPE_SCNAME   ON ADDRESSOBJECTTYPE(SCNAME);
CREATE INDEX IDX_CURRENTSTATUS_NAME         ON CURRENTSTATUS(NAME);
CREATE INDEX IDX_ACTUALSTATUS_NAME          ON ACTUALSTATUS(NAME);
CREATE INDEX IDX_OPERATIONSTATUS_NAME       ON OPERATIONSTATUS(NAME);
CREATE INDEX IDX_CENTERSTATUS_NAME          ON CENTERSTATUS(NAME);
CREATE INDEX IDX_INTERVALSTATUS_NAME        ON INTERVALSTATUS(NAME);
CREATE INDEX IDX_HOUSESTATESTATUS_NAME      ON HOUSESTATESTATUS(NAME);
CREATE INDEX IDX_ESTATESTATUS_NAME          ON ESTATESTATUS(NAME);
CREATE INDEX IDX_ESTATESTATUS_SHORTNAME     ON ESTATESTATUS(SHORTNAME);
CREATE INDEX IDX_STRUCTURESTATUS_NAME       ON STRUCTURESTATUS(NAME);
CREATE INDEX IDX_STRUCTURESTATUS_SHORTNAME  ON STRUCTURESTATUS(SHORTNAME);

--ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_PARENTGUID         FOREIGN KEY(PARENTGUID) REFERENCES OBJECT(AOGUID);
--ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_PREVID             FOREIGN KEY(PREVID)     REFERENCES OBJECT(AOID);
--ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_NEXTID             FOREIGN KEY(NEXTID)     REFERENCES OBJECT(AOID);
--ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_SHORTNAME          FOREIGN KEY(SHORTNAME)  REFERENCES ADDRESSOBJECTTYPE(SCNAME);
ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_ACTSTATUS          FOREIGN KEY(ACTSTATUS)  REFERENCES ACTUALSTATUS(ACTSTATID);
ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_CENTSTATUS         FOREIGN KEY(CENTSTATUS) REFERENCES CENTERSTATUS(CENTERSTID);
ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_OPERSTATUS         FOREIGN KEY(OPERSTATUS) REFERENCES OPERATIONSTATUS(OPERSTATID);
ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_CURRSTATUS         FOREIGN KEY(CURRSTATUS) REFERENCES CURRENTSTATUS(CURENTSTID);
--ALTER TABLE OBJECT            ADD CONSTRAINT FK_OBJECT_NORMDOC            FOREIGN KEY(NORMDOC)    REFERENCES NORMATIVEDOCUMENT(NORMDOCID);
--ALTER TABLE HOUSE             ADD CONSTRAINT FK_HOUSE_AOGUID              FOREIGN KEY(AOGUID)     REFERENCES OBJECT(AOGUID);
ALTER TABLE HOUSE             ADD CONSTRAINT FK_HOUSE_ESTSTATUS           FOREIGN KEY(ESTSTATUS)  REFERENCES ESTATESTATUS(ESTSTATID);
ALTER TABLE HOUSE             ADD CONSTRAINT FK_HOUSE_STRSTATUS           FOREIGN KEY(STRSTATUS)  REFERENCES STRUCTURESTATUS(STRSTATID);
ALTER TABLE HOUSE             ADD CONSTRAINT FK_HOUSE_STATSTATUS          FOREIGN KEY(STATSTATUS) REFERENCES HOUSESTATESTATUS(HOUSESTID);
--ALTER TABLE HOUSE             ADD CONSTRAINT FK_HOUSE_NORMDOC             FOREIGN KEY(NORMDOC)    REFERENCES NORMATIVEDOCUMENT(NORMDOCID);
--ALTER TABLE HOUSEINTERVAL     ADD CONSTRAINT FK_HOUSEINTERVAL_AOGUID      FOREIGN KEY(AOGUID)     REFERENCES OBJECT(AOGUID);
ALTER TABLE HOUSEINTERVAL     ADD CONSTRAINT FK_HOUSEINTERVAL_INTSTATUS   FOREIGN KEY(INTSTATUS)  REFERENCES INTERVALSTATUS(INTVSTATID);
--ALTER TABLE HOUSEINTERVAL     ADD CONSTRAINT FK_HOUSEINTERVAL_NORMDOC     FOREIGN KEY(NORMDOC)    REFERENCES NORMATIVEDOCUMENT(NORMDOCID);
--ALTER TABLE LANDMARK          ADD CONSTRAINT FK_LANDMARK_AOGUID           FOREIGN KEY(AOGUID)     REFERENCES OBJECT(AOGUID);
--ALTER TABLE LANDMARK          ADD CONSTRAINT FK_LANDMARK_NORMDOC          FOREIGN KEY(NORMDOC)    REFERENCES NORMATIVEDOCUMENT(NORMDOCID);
ALTER TABLE NORMATIVEDOCUMENT ADD CONSTRAINT FK_NORMATIVEDOCUMENT_DOCTYPE FOREIGN KEY(DOCTYPE)    REFERENCES NORMATIVEDOCUMENTTYPE(NDTYPEID);