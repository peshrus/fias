-- СВЕДЕНИЯ О СОСТАВЕ ИНФОРМАЦИИ ФЕДЕРАЛЬНОЙ ИНФОРМАЦИОННОЙ АДРЕСНОЙ СИСТЕМЫ

-- 2.2.  Описание элементов КЛАДЭ

CREATE TABLE OBJECT (
	AOGUID      NVARCHAR(36)   NOT NULL COMMENT 'Глобальный уникальный идентификатор адресного объекта',
	FORMALNAME  NVARCHAR(120)  NOT NULL COMMENT 'Формализованное наименование',
	REGIONCODE  NVARCHAR(2)    NOT NULL COMMENT 'Код региона',
	AUTOCODE    NVARCHAR(1)    NOT NULL COMMENT 'Код автономии',
	AREACODE    NVARCHAR(3)    NOT NULL COMMENT 'Код района',
	CITYCODE    NVARCHAR(3)    NOT NULL COMMENT 'Код города',
	CTARCODE    NVARCHAR(3)    NOT NULL COMMENT 'Код внутригородского района',
	PLACECODE   NVARCHAR(3)    NOT NULL COMMENT 'Код населенного пункта',
	STREETCODE  NVARCHAR(4)    NOT NULL COMMENT 'Код улицы',
	EXTRCODE    NVARCHAR(4)    NOT NULL COMMENT 'Код дополнительного адресообразующего элемента',
	SEXTCODE    NVARCHAR(3)    NOT NULL COMMENT 'Код подчиненного дополнительного адресообразующего элемента',
	OFFNAME     NVARCHAR(120)           COMMENT 'Официальное наименование. Содержит наименование и тип адресного объекта, введенное соответствующим нормативным документом  органом исполнительной  власти, решением, постановлением муниципального образования. Используется при формировании документов и почтовых отправлений',
	POSTALCODE  NVARCHAR(6)             COMMENT 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект',
	IFNSFL      NVARCHAR(4)             COMMENT 'Код ИФНС ФЛ. Содержит код инспекции ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSFL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ФЛ. Содержит код территориального участка (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	IFNSUL      NVARCHAR(4)             COMMENT 'Код ИФНС ЮЛ. Содержит код инспекции ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSUL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ЮЛ. Содержит код территориального участка (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	OKATO       NVARCHAR(11)            COMMENT 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов). Для адресных объектов, не включенных в классификатор ОКАТО, в этом поле указывается код ОКАТО либо старшего административно-территориального объекта, либо расположенного в непосредственной близости к адресуемому объекту административно-территориального объекта, включенного в ОКАТО',
	OKTMO       NVARCHAR(8)             COMMENT 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект',
	UPDATEDATE  DATE           NOT NULL COMMENT 'Дата  внесения (обновления) записи',
	SHORTNAME   NVARCHAR(10)   NOT NULL COMMENT 'Краткое наименование типа объекта. Cодержит краткое наименование типа адресного объекта из файла AddressObjectType',
	AOLEVEL     NUMBER(10)     NOT NULL COMMENT 'Уровень адресного объекта. Содержит номер уровня классификации адресных объектов. Перечень уровней адресных объектов и соответствующих им типов адресных объектов определен в таблице AddressObjectType ФИАС. Условно выделены следующие уровни адресных объектов: 1 – уровень региона; 2 – уровень автономного округа; 3 – уровень района; 4 – уровень города; 5 – уровень внутригородской территории; 6 – уровень населенного пункта; 7 – уровень улицы; 90 – уровень дополнительных территорий; 91 – уровень подчиненных дополнительным территориям объектов',
	PARENTGUID  NVARCHAR(36)            COMMENT 'Идентификатор объекта родительского объекта. Используется для связи с вышестоящими адресными объектами',
	AOID        NVARCHAR(36)   NOT NULL COMMENT 'Уникальный идентификатор записи. Ключевое поле',
	PREVID      NVARCHAR(36)            COMMENT 'Идентификатор записи связывания с предыдушей исторической записью. Используется для связи с предыдущим историческим наименованием адресного объекта',
	NEXTID      NVARCHAR(36)            COMMENT 'Идентификатор записи  связывания с последующей исторической записью. Используется для связи с предыдущим историческим наименованием адресного объекта',
	CODE        NVARCHAR(17)            COMMENT 'Код адресного объекта одной строкой с признаком актуальности из КЛАДР 4.0',
	PLAINCODE   NVARCHAR(15)            COMMENT 'Код адресного объекта из КЛАДР 4.0 одной строкой без признака актуальности (последних двух цифр)',
	ACTSTATUS   NUMBER(10)     NOT NULL COMMENT 'Статус актуальности адресного объекта ФИАС. Определяет, является ли эта запись по адресному объекту актуальной (самой последней) на текущую дату. Принимает значение: 0 – Не актуальный; 1 – Актуальный',
	CENTSTATUS  NUMBER(10)     NOT NULL COMMENT 'Статус центра. Содержит значение признака ("признак центра"), которое определяет, является ли данный адресный объект центром административно - территориального образования: столицей республики, центром края, области, района и т.п. Данный блок может содержать следующие значения: 0 – объект не является центром административно-территориального образования; 1 – объект является центром района; 2 – объект является центром (столицей) региона; 3 – объект является одновременно и центром района и центром региона',
	OPERSTATUS  NUMBER(10)     NOT NULL COMMENT 'Статус действия над записью – причина появления записи. Принимает значение: 01 – Инициация; 10 – Добавление; 20 – Изменение; 21 – Групповое изменение; 30 – Удаление; 31 – Удаление вследствие удаления вышестоящего объекта; 40 – Присоединение адресного объекта (слияние); 41 – Переподчинение вследствие слияния вышестоящего объекта; 42 – Прекращение существования вследствие присоединения к другому адресному объекту; 43 – Создание нового адресного объекта в результате слияния адресных объектов; 50 – Переподчинение; 51 – Переподчинение вследствие переподчинения вышестоящего объекта; 60 – Прекращение существования вследствие дробления; 61 – Создание нового адресного объекта в результате дробления; 70 – Восстановление прекратившего существование объекта',
	CURRSTATUS  NUMBER(10)     NOT NULL COMMENT 'Статус актуальности КЛАДР 4 (последние две цифры в коде)',
	STARTDATE   DATE           NOT NULL COMMENT 'Начало действия записи',
	ENDDATE     DATE           NOT NULL COMMENT 'Окончание действия записи',
	NORMDOC     NVARCHAR(36)            COMMENT 'Внешний ключ на нормативный документ',
	LIVESTATUS  NUMBER(1)      NOT NULL COMMENT 'Признак действующего адресного объекта: 0 – недействующий адресный объект; 1 - действующий'
);
COMMENT ON TABLE OBJECT IS 'Состав элементов КЛАДЭ';

-- 3. Сведения об  элементах адреса, идентифицирующих адресуемые объекты

CREATE TABLE HOUSE (
	POSTALCODE  NVARCHAR(6)             COMMENT 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект',
	IFNSFL      NVARCHAR(4)             COMMENT 'Код ИФНС ФЛ. Содержит код инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSFL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ФЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	IFNSUL      NVARCHAR(4)             COMMENT 'Код ИФНС ЮЛ. Содержит код инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSUL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ЮЛ.Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	OKATO       NVARCHAR(11)            COMMENT 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня,  на территории которого находится дом, строение, земельный участок (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов)',
	OKTMO       NVARCHAR(8)             COMMENT 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект',
	UPDATEDATE  DATE           NOT NULL COMMENT 'Дата время внесения (обновления) записи',
	HOUSENUM    NVARCHAR(20)            COMMENT 'Номер дома (земельного участка), корпуса, строения',
	ESTSTATUS   NUMBER(1)      NOT NULL COMMENT 'Признак владения. Принимает следующие значения: 1 – владение; 2 – дом; 3 – домовладение; 4 – участок',
	BUILDNUM    NVARCHAR(10)            COMMENT 'Номер корпуса',
	STRUCNUM    NVARCHAR(10)            COMMENT 'Номер строения',
	STRSTATUS   NUMBER(10)     NOT NULL COMMENT 'Признак строения. Принимает следующие значения: 1 – строение; 2 – сооружение; 3 – литер',
	HOUSEID     NVARCHAR(36)   NOT NULL COMMENT 'Уникальный идентификатор записи дома. Используется для связи с нижестоящими адресными объектами',
	HOUSEGUID   NVARCHAR(36)   NOT NULL COMMENT 'Глобальный уникальный идентификатор дома',
	AOGUID      NVARCHAR(36)   NOT NULL COMMENT 'Guid записи родительского объекта (улицы, города, населенного пункта и т.п.). Содержит идентификационный код адресного объекта по классификатору адресообразующих элементов, на территории которого находится дом',
	STARTDATE   DATE           NOT NULL COMMENT 'Начало действия записи',
	ENDDATE     DATE           NOT NULL COMMENT 'Окончание действия записи',
	STATSTATUS  NUMBER(10)     NOT NULL COMMENT 'Состояние дома. Содержит коды состояния домов, используемые в БТИ',
	NORMDOC     NVARCHAR(36)            COMMENT 'Внешний ключ на нормативный документ',
	COUNTER     NUMBER(10)     NOT NULL COMMENT 'Счетчик записей домов для КЛАДР 4'
);
COMMENT ON TABLE HOUSE IS 'Информация о номерах отдельных домов, владений, домовладений, корпусов, строений и земельных участках';

CREATE TABLE HOUSEINTERVAL (
	POSTALCODE  NVARCHAR(6)             COMMENT 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект',
	IFNSFL      NVARCHAR(4)             COMMENT 'Код ИФНС ФЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSFL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ФЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	IFNSUL      NVARCHAR(4)             COMMENT 'Код ИФНС ЮЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSUL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ЮЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	OKATO       NVARCHAR(11)            COMMENT 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня, на территории которого находится дома с указанным интервалом (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов)',
	OKTMO       NVARCHAR(8)             COMMENT 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект',
	UPDATEDATE  DATE           NOT NULL COMMENT 'Дата  внесения (обновления) записи',
	INTSTART    NUMBER(10)     NOT NULL COMMENT 'Значение начала интервала. Определяет начало интервала номеров домов. Для четных интервалов обозначается четной цифрой, для нечетных интервалов – нечетной',
	INTEND      NUMBER(10)     NOT NULL COMMENT 'Значение окончания интервала. Определяет конец интервала номеров домов. Для четных интервалов обозначается четной цифрой, для нечетных интервалов – нечетной, для обычных интервалов – любой цифрой',
	HOUSEINTID  NVARCHAR(36)   NOT NULL COMMENT 'Идентификатор записи интервала домов',
	INTGUID     NVARCHAR(36)   NOT NULL COMMENT 'Глобальный уникальный идентификатор интервала домов',
	AOGUID      NVARCHAR(36)   NOT NULL COMMENT 'Идентификатор объекта родительского объекта (улицы, города, населенного пункта и т.п.). Содержит идентификационный код объекта адресного объекта по классификатору адресообразующих элементов, на территории которого дома из данного интервала',
	STARTDATE   DATE           NOT NULL COMMENT 'Начало действия записи',
	ENDDATE     DATE           NOT NULL COMMENT 'Окончание действия записи',
	INTSTATUS   NUMBER(10)     NOT NULL COMMENT 'Статус интервала (обычный, четный, нечетный). Может принимать следующие значения: 1 – для обычных интервалов, включающих четные и нечетные номера домов; 2 – для четных интервалов, включающих четные номера домов; 3 – для нечетных интервалов, включающих нечетные номера домов',
	NORMDOC     NVARCHAR(36)            COMMENT 'Внешний ключ на нормативный документ',
	COUNTER     NUMBER(10)     NOT NULL COMMENT 'Счетчик записей домов для КЛАДР 4'
);
COMMENT ON TABLE HOUSEINTERVAL IS 'Информация об интервалах номеров домов. При этом предполагается, что в указанных интервалах содержатся сведения обо всех номерах домов, владений, домовладений, корпусов и строений и их сочетаний';

CREATE TABLE LANDMARK (
	LOCATION    NVARCHAR(500)  NOT NULL COMMENT 'Месторасположение ориентира. содержит описание места нахождения объекта недвижимости (“ориентир”), для которого нельзя указать точный структурированный адрес (почтовый адрес). Значение блока записывается в произвольном виде, позволяющем однозначно определить местонахождение объекта недвижимости через известные ориентиры на местности',
	POSTALCODE  NVARCHAR(6)             COMMENT 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект',
	IFNSFL      NVARCHAR(4)             COMMENT 'Код ИФНС ФЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSFL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ФЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект',
	IFNSUL      NVARCHAR(4)             COMMENT 'Код ИФНС ЮЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	TERRIFNSUL  NVARCHAR(4)             COMMENT 'Код территориального участка ИФНС ЮЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект',
	OKATO       NVARCHAR(11)            COMMENT 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня, на территории которого находится адресуемый объект (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов)',
	OKTMO       NVARCHAR(8)             COMMENT 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект',
	UPDATEDATE  DATE           NOT NULL COMMENT 'Дата  внесения (обновления) записи',
	LANDID      NVARCHAR(36)   NOT NULL COMMENT 'Уникальный идентификатор записи ориентира', 
	LANDGUID    NVARCHAR(36)   NOT NULL COMMENT 'Глобальный уникальный идентификатор ориентира',
	AOGUID      NVARCHAR(36)   NOT NULL COMMENT 'Уникальный идентификатор родительского объекта (улицы, города, населенного пункта и т.п.). Содержит идентификационный код адресного объекта по классификатору   адресообразующих элементов, на территории которого находится адресуемый объект',
	STARTDATE   DATE           NOT NULL COMMENT 'Начало действия записи',
	ENDDATE     DATE           NOT NULL COMMENT 'Окончание действия записи',
	NORMDOC     NVARCHAR(36)            COMMENT 'Внешний ключ на нормативный документ'
);
COMMENT ON TABLE LANDMARK IS 'Дополнительное неформализованное текстовое описание места расположения объекта адресации относительно ориентиров на местности';

-- Состав и структура файла с информацией по нормативным документам, являющимся основанием присвоения адресному элементу наименования в базе данных Федеральной информационной справочной системы

CREATE TABLE NORMATIVEDOCUMENT (
	NORMDOCID   NVARCHAR(36)   NOT NULL COMMENT 'Идентификатор нормативного документа',
	DOCNAME     NCLOB                   COMMENT 'Наименование документа',
	DOCDATE     DATE                    COMMENT 'Дата документа',
	DOCNUM      NVARCHAR(20)            COMMENT 'Номер документа',
	DOCTYPE     NUMBER(10)     NOT NULL COMMENT 'Тип документа',
	DOCIMGID    NUMBER(10)              COMMENT 'Идентификатор образа (внешний ключ)'
);
COMMENT ON TABLE NORMATIVEDOCUMENT IS 'Сведения по нормативному документу, являющемуся основанием присвоения адресному элементу наименования';

CREATE TABLE NORMATIVEDOCUMENTTYPE (
	NDTYPEID    NUMBER(10)     NOT NULL COMMENT 'Идентификатор типа нормативного документа',
	NAME        NVARCHAR(100)  NOT NULL COMMENT 'Наименование'
);
COMMENT ON TABLE NORMATIVEDOCUMENTTYPE IS 'Сведения по типу нормативного документа, являющегося основанием присвоения адресному элементу наименования';

-- Справочные сведения

CREATE TABLE ADDRESSOBJECTTYPE (
	LEVEL       NUMBER(10)     NOT NULL COMMENT 'Уровень адресного объекта',
	SCNAME      NVARCHAR(10)            COMMENT 'Краткое наименование типа объекта',
	SOCRNAME    NVARCHAR(50)   NOT NULL COMMENT 'Полное наименование типа объекта',
	KOD_T_ST    NVARCHAR(4)    NOT NULL COMMENT 'Ключевое поле'
);
COMMENT ON TABLE ADDRESSOBJECTTYPE IS 'Тип адресного объекта';

CREATE TABLE CURRENTSTATUS (
	CURENTSTID  NUMBER(10)     NOT NULL COMMENT 'Идентификатор статуса (ключ). Принимает значения: 0 – актуальный; 1-50, 2-98 – исторический; 51 – переподчиненный; 99 - несуществующий',
	NAME        NVARCHAR(100)  NOT NULL COMMENT 'Наименование'
);
COMMENT ON TABLE CURRENTSTATUS IS 'Статус актуальности КЛАДР 4.0';

CREATE TABLE ACTUALSTATUS (
	ACTSTATID   NUMBER(10)     NOT NULL COMMENT 'Идентификатор статуса (ключ). Принимает значения: 0 – Не актуальный; 1 – Актуальный (последняя запись по адресному объекту)',
	NAME        NVARCHAR(100)  NOT NULL COMMENT 'Наименование'
);
COMMENT ON TABLE ACTUALSTATUS IS 'Статус актуальности ФИАС';

CREATE TABLE OPERATIONSTATUS (
	OPERSTATID  NUMBER(10)     NOT NULL COMMENT 'Идентификатор статуса (ключ). Принимает значения: 01 – Инициация; 10 – Добавление; 20 – Изменение; 21 – Групповое изменение; 30 – Удаление; 31 - Удаление вследствие удаления вышестоящего объекта; 40 – Присоединение адресного объекта (слияние); 41 – Переподчинение вследствие слияния вышестоящего объекта; 42 - Прекращение существования вследствие присоединения к другому адресному объекту; 43 - Создание нового адресного объекта в результате слияния адресных объектов; 50 – Переподчинение; 51 – Переподчинение вследствие переподчинения вышестоящего объекта; 60 – Прекращение существования вследствие дробления; 61 – Создание нового адресного объекта в результате дробления; 70 – Восстановление прекратившего существование объекта',
	NAME        NVARCHAR(100)  NOT NULL COMMENT 'Наименование'
);
COMMENT ON TABLE OPERATIONSTATUS IS 'Статус действия';

CREATE TABLE CENTERSTATUS (
	CENTERSTID  NUMBER(10)     NOT NULL COMMENT 'Идентификатор статуса',
	NAME        NVARCHAR(100)  NOT NULL COMMENT 'Наименование'
);
COMMENT ON TABLE CENTERSTATUS IS 'Статус центра';

CREATE TABLE INTERVALSTATUS (
	INTVSTATID  NUMBER(10)     NOT NULL COMMENT 'Идентификатор статуса (обычный, четный, нечетный). Принимает значения: 0 – Не определено; 1 – Обычный; 2 – Четный; 3 – Нечетный',
	NAME        NVARCHAR(60)   NOT NULL COMMENT 'Наименование'
);
COMMENT ON TABLE INTERVALSTATUS IS 'Статус интервала домов';

CREATE TABLE HOUSESTATESTATUS (
	HOUSESTID   NUMBER(10)     NOT NULL COMMENT 'Идентификатор статуса',
	NAME        NVARCHAR(60)   NOT NULL COMMENT 'Наименование'
);
COMMENT ON TABLE HOUSESTATESTATUS IS 'Статус состояния домов';

CREATE TABLE ESTATESTATUS (
	ESTSTATID   NUMBER(10)     NOT NULL COMMENT 'Признак владения. Принимает значение: 0 – Не определено; 1 – Владение; 2 – Дом; 3 – Домовладение',
	NAME        NVARCHAR(20)   NOT NULL COMMENT 'Наименование',
	SHORTNAME   NVARCHAR(20)           COMMENT 'Краткое наименование'
);
COMMENT ON TABLE ESTATESTATUS IS 'Признак владения';

CREATE TABLE STRUCTURESTATUS (
	STRSTATID   NUMBER(10)     NOT NULL COMMENT 'Признак строения. Принимает значение: 0 – Не определено; 1 – Строение; 2 – Сооружение; 3 – Литер',
	NAME        NVARCHAR(20)   NOT NULL COMMENT 'Наименование',
	SHORTNAME   NVARCHAR(20)            COMMENT 'Краткое наименование'
);
COMMENT ON TABLE STRUCTURESTATUS IS 'Признак строения';

-- Приложение 3. Код субъектов Российской Федерации (регионов)

CREATE TABLE REGION (
	CODE        VARCHAR(2)    NOT NULL,
	NAME        VARCHAR(120)  NOT NULL
);

INSERT INTO REGION(CODE, NAME) VALUES('01', 'Республика Адыгея (Адыгея)');
INSERT INTO REGION(CODE, NAME) VALUES('02', 'Республика Башкортостан');
INSERT INTO REGION(CODE, NAME) VALUES('03', 'Республика Бурятия');
INSERT INTO REGION(CODE, NAME) VALUES('04', 'Республика Алтай');
INSERT INTO REGION(CODE, NAME) VALUES('05', 'Республика Дагестан');
INSERT INTO REGION(CODE, NAME) VALUES('06', 'Республика Ингушетия');
INSERT INTO REGION(CODE, NAME) VALUES('07', 'Кабардино-Балкарская Республика');
INSERT INTO REGION(CODE, NAME) VALUES('08', 'Республика Калмыкия');
INSERT INTO REGION(CODE, NAME) VALUES('09', 'Карачаево-Черкесская Республика');
INSERT INTO REGION(CODE, NAME) VALUES('10', 'Республика Карелия');
INSERT INTO REGION(CODE, NAME) VALUES('11', 'Республика Коми');
INSERT INTO REGION(CODE, NAME) VALUES('12', 'Республика Марий Эл');
INSERT INTO REGION(CODE, NAME) VALUES('13', 'Республика Мордовия');
INSERT INTO REGION(CODE, NAME) VALUES('14', 'Республика Саха (Якутия)');
INSERT INTO REGION(CODE, NAME) VALUES('15', 'Республика Северная Осетия - Алания');
INSERT INTO REGION(CODE, NAME) VALUES('16', 'Республика Татарстан (Татарстан)');
INSERT INTO REGION(CODE, NAME) VALUES('17', 'Республика Тыва');
INSERT INTO REGION(CODE, NAME) VALUES('18', 'Удмуртская Республика');
INSERT INTO REGION(CODE, NAME) VALUES('19', 'Республика Хакасия');
INSERT INTO REGION(CODE, NAME) VALUES('20', 'Чеченская Республика');
INSERT INTO REGION(CODE, NAME) VALUES('21', 'Чувашская Республика - Чувашия');
INSERT INTO REGION(CODE, NAME) VALUES('22', 'Алтайский край');
INSERT INTO REGION(CODE, NAME) VALUES('23', 'Краснодарский край');
INSERT INTO REGION(CODE, NAME) VALUES('24', 'Красноярский край');
INSERT INTO REGION(CODE, NAME) VALUES('25', 'Приморский край');
INSERT INTO REGION(CODE, NAME) VALUES('26', 'Ставропольский край');
INSERT INTO REGION(CODE, NAME) VALUES('27', 'Хабаровский край');
INSERT INTO REGION(CODE, NAME) VALUES('28', 'Амурская область');
INSERT INTO REGION(CODE, NAME) VALUES('29', 'Архангельская область');
INSERT INTO REGION(CODE, NAME) VALUES('30', 'Астраханская область');
INSERT INTO REGION(CODE, NAME) VALUES('31', 'Белгородская область');
INSERT INTO REGION(CODE, NAME) VALUES('32', 'Брянская область');
INSERT INTO REGION(CODE, NAME) VALUES('33', 'Владимирская область');
INSERT INTO REGION(CODE, NAME) VALUES('34', 'Волгоградская область');
INSERT INTO REGION(CODE, NAME) VALUES('35', 'Вологодская область');
INSERT INTO REGION(CODE, NAME) VALUES('36', 'Воронежская область');
INSERT INTO REGION(CODE, NAME) VALUES('37', 'Ивановская область');
INSERT INTO REGION(CODE, NAME) VALUES('38', 'Иркутская область');
INSERT INTO REGION(CODE, NAME) VALUES('39', 'Калининградская область');
INSERT INTO REGION(CODE, NAME) VALUES('40', 'Калужская область');
INSERT INTO REGION(CODE, NAME) VALUES('41', 'Камчатский край');
INSERT INTO REGION(CODE, NAME) VALUES('42', 'Кемеровская область');
INSERT INTO REGION(CODE, NAME) VALUES('43', 'Кировская область');
INSERT INTO REGION(CODE, NAME) VALUES('44', 'Костромская область');
INSERT INTO REGION(CODE, NAME) VALUES('45', 'Курганская область');
INSERT INTO REGION(CODE, NAME) VALUES('46', 'Курская область');
INSERT INTO REGION(CODE, NAME) VALUES('47', 'Ленинградская область');
INSERT INTO REGION(CODE, NAME) VALUES('48', 'Липецкая область');
INSERT INTO REGION(CODE, NAME) VALUES('49', 'Магаданская область');
INSERT INTO REGION(CODE, NAME) VALUES('50', 'Московская область');
INSERT INTO REGION(CODE, NAME) VALUES('51', 'Мурманская область');
INSERT INTO REGION(CODE, NAME) VALUES('52', 'Нижегородская область');
INSERT INTO REGION(CODE, NAME) VALUES('53', 'Новгородская область');
INSERT INTO REGION(CODE, NAME) VALUES('54', 'Новосибирская область');
INSERT INTO REGION(CODE, NAME) VALUES('55', 'Омская область');
INSERT INTO REGION(CODE, NAME) VALUES('56', 'Оренбургская область');
INSERT INTO REGION(CODE, NAME) VALUES('57', 'Орловская область');
INSERT INTO REGION(CODE, NAME) VALUES('58', 'Пензенская область');
INSERT INTO REGION(CODE, NAME) VALUES('59', 'Пермский край');
INSERT INTO REGION(CODE, NAME) VALUES('60', 'Псковская область');
INSERT INTO REGION(CODE, NAME) VALUES('61', 'Ростовская область');
INSERT INTO REGION(CODE, NAME) VALUES('62', 'Рязанская область');
INSERT INTO REGION(CODE, NAME) VALUES('63', 'Самарская область');
INSERT INTO REGION(CODE, NAME) VALUES('64', 'Саратовская область');
INSERT INTO REGION(CODE, NAME) VALUES('65', 'Сахалинская область');
INSERT INTO REGION(CODE, NAME) VALUES('66', 'Свердловская область');
INSERT INTO REGION(CODE, NAME) VALUES('67', 'Смоленская область');
INSERT INTO REGION(CODE, NAME) VALUES('68', 'Тамбовская область');
INSERT INTO REGION(CODE, NAME) VALUES('69', 'Тверская область');
INSERT INTO REGION(CODE, NAME) VALUES('70', 'Томская область');
INSERT INTO REGION(CODE, NAME) VALUES('71', 'Тульская область');
INSERT INTO REGION(CODE, NAME) VALUES('72', 'Тюменская область');
INSERT INTO REGION(CODE, NAME) VALUES('73', 'Ульяновская область');
INSERT INTO REGION(CODE, NAME) VALUES('74', 'Челябинская область');
INSERT INTO REGION(CODE, NAME) VALUES('75', 'Забайкальский край');
INSERT INTO REGION(CODE, NAME) VALUES('76', 'Ярославская область');
INSERT INTO REGION(CODE, NAME) VALUES('77', 'Г.Москва');
INSERT INTO REGION(CODE, NAME) VALUES('78', 'Г.Санкт-Петербург');
INSERT INTO REGION(CODE, NAME) VALUES('79', 'Еврейская автономная область');
INSERT INTO REGION(CODE, NAME) VALUES('83', 'Ненецкий автономный округ');
INSERT INTO REGION(CODE, NAME) VALUES('86', 'Ханты-Мансийский автономный округ - Югра');
INSERT INTO REGION(CODE, NAME) VALUES('87', 'Чукотский автономный округ');
INSERT INTO REGION(CODE, NAME) VALUES('89', 'Ямало-Ненецкий автономный округ');
INSERT INTO REGION(CODE, NAME) VALUES('99', 'Иные территории, включая город и космодром Байконур');