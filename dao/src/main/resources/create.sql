-- СВЕДЕНИЯ О СОСТАВЕ ИНФОРМАЦИИ ФЕДЕРАЛЬНОЙ ИНФОРМАЦИОННОЙ АДРЕСНОЙ СИСТЕМЫ

-- 2.2.  Описание элементов КЛАДЭ

CREATE TABLE OBJECT (
	AOGUID      VARCHAR(36)   NOT NULL,
	FORMALNAME  VARCHAR(120)  NOT NULL,
	REGIONCODE  VARCHAR(2)    NOT NULL,
	AUTOCODE    VARCHAR(1)    NOT NULL,
	AREACODE    VARCHAR(3)    NOT NULL,
	CITYCODE    VARCHAR(3)    NOT NULL,
	CTARCODE    VARCHAR(3)    NOT NULL,
	PLACECODE   VARCHAR(3)    NOT NULL,
	STREETCODE  VARCHAR(4)    NOT NULL,
	EXTRCODE    VARCHAR(4)    NOT NULL,
	SEXTCODE    VARCHAR(3)    NOT NULL,
	OFFNAME     VARCHAR(120),
	POSTALCODE  VARCHAR(6),
	IFNSFL      VARCHAR(4),
	TERRIFNSFL  VARCHAR(4),
	IFNSUL      VARCHAR(4),
	TERRIFNSUL  VARCHAR(4),
	OKATO       VARCHAR(11),
	OKTMO       VARCHAR(8),
	UPDATEDATE  DATE          NOT NULL,
	SHORTNAME   VARCHAR(10)   NOT NULL,
	AOLEVEL     DECIMAL(10)   NOT NULL,
	PARENTGUID  VARCHAR(36),
	AOID        VARCHAR(36)   NOT NULL,
	PREVID      VARCHAR(36),
	NEXTID      VARCHAR(36),
	CODE        VARCHAR(17),
	PLAINCODE   VARCHAR(15),
	ACTSTATUS   DECIMAL(10)   NOT NULL,
	CENTSTATUS  DECIMAL(10)   NOT NULL,
	OPERSTATUS  DECIMAL(10)   NOT NULL,
	CURRSTATUS  DECIMAL(10)   NOT NULL,
	STARTDATE   DATE          NOT NULL,
	ENDDATE     DATE          NOT NULL,
	NORMDOC     VARCHAR(36),
	LIVESTATUS  DECIMAL(1)    NOT NULL
);
COMMENT ON TABLE  OBJECT            IS 'Состав элементов КЛАДЭ';
COMMENT ON COLUMN OBJECT.AOGUID     IS 'Глобальный уникальный идентификатор адресного объекта';
COMMENT ON COLUMN OBJECT.FORMALNAME IS 'Формализованное наименование';
COMMENT ON COLUMN OBJECT.REGIONCODE IS 'Код региона';
COMMENT ON COLUMN OBJECT.AUTOCODE   IS 'Код автономии';
COMMENT ON COLUMN OBJECT.AREACODE   IS 'Код района';
COMMENT ON COLUMN OBJECT.CITYCODE   IS 'Код города';
COMMENT ON COLUMN OBJECT.CTARCODE   IS 'Код внутригородского района';
COMMENT ON COLUMN OBJECT.PLACECODE  IS 'Код населенного пункта';
COMMENT ON COLUMN OBJECT.STREETCODE IS 'Код улицы';
COMMENT ON COLUMN OBJECT.EXTRCODE   IS 'Код дополнительного адресообразующего элемента';
COMMENT ON COLUMN OBJECT.SEXTCODE   IS 'Код подчиненного дополнительного адресообразующего элемента';
COMMENT ON COLUMN OBJECT.OFFNAME    IS 'Официальное наименование. Содержит наименование и тип адресного объекта, введенное соответствующим нормативным документом  органом исполнительной  власти, решением, постановлением муниципального образования. Используется при формировании документов и почтовых отправлений';
COMMENT ON COLUMN OBJECT.POSTALCODE IS 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект';
COMMENT ON COLUMN OBJECT.IFNSFL     IS 'Код ИФНС ФЛ. Содержит код инспекции ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN OBJECT.TERRIFNSFL IS 'Код территориального участка ИФНС ФЛ. Содержит код территориального участка (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN OBJECT.IFNSUL     IS 'Код ИФНС ЮЛ. Содержит код инспекции ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN OBJECT.TERRIFNSUL IS 'Код территориального участка ИФНС ЮЛ. Содержит код территориального участка (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN OBJECT.OKATO      IS 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов). Для адресных объектов, не включенных в классификатор ОКАТО, в этом поле указывается код ОКАТО либо старшего административно-территориального объекта, либо расположенного в непосредственной близости к адресуемому объекту административно-территориального объекта, включенного в ОКАТО';
COMMENT ON COLUMN OBJECT.OKTMO      IS 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект';
COMMENT ON COLUMN OBJECT.UPDATEDATE IS 'Дата  внесения (обновления) записи';
COMMENT ON COLUMN OBJECT.SHORTNAME  IS 'Краткое наименование типа объекта. Cодержит краткое наименование типа адресного объекта из файла AddressObjectType';
COMMENT ON COLUMN OBJECT.AOLEVEL    IS 'Уровень адресного объекта. Содержит номер уровня классификации адресных объектов. Перечень уровней адресных объектов и соответствующих им типов адресных объектов определен в таблице AddressObjectType ФИАС. Условно выделены следующие уровни адресных объектов: 1 – уровень региона; 2 – уровень автономного округа; 3 – уровень района; 4 – уровень города; 5 – уровень внутригородской территории; 6 – уровень населенного пункта; 7 – уровень улицы; 90 – уровень дополнительных территорий; 91 – уровень подчиненных дополнительным территориям объектов';
COMMENT ON COLUMN OBJECT.PARENTGUID IS 'Идентификатор объекта родительского объекта. Используется для связи с вышестоящими адресными объектами';
COMMENT ON COLUMN OBJECT.AOID       IS 'Уникальный идентификатор записи. Ключевое поле';
COMMENT ON COLUMN OBJECT.PREVID     IS 'Идентификатор записи связывания с предыдушей исторической записью. Используется для связи с предыдущим историческим наименованием адресного объекта';
COMMENT ON COLUMN OBJECT.NEXTID     IS 'Идентификатор записи  связывания с последующей исторической записью. Используется для связи с предыдущим историческим наименованием адресного объекта';
COMMENT ON COLUMN OBJECT.CODE       IS 'Код адресного объекта одной строкой с признаком актуальности из КЛАДР 4.0';
COMMENT ON COLUMN OBJECT.PLAINCODE  IS 'Код адресного объекта из КЛАДР 4.0 одной строкой без признака актуальности (последних двух цифр)';
COMMENT ON COLUMN OBJECT.ACTSTATUS  IS 'Статус актуальности адресного объекта ФИАС. Определяет, является ли эта запись по адресному объекту актуальной (самой последней) на текущую дату. Принимает значение: 0 – Не актуальный; 1 – Актуальный';
COMMENT ON COLUMN OBJECT.CENTSTATUS IS 'Статус центра. Содержит значение признака ("признак центра"), которое определяет, является ли данный адресный объект центром административно - территориального образования: столицей республики, центром края, области, района и т.п. Данный блок может содержать следующие значения: 0 – объект не является центром административно-территориального образования; 1 – объект является центром района; 2 – объект является центром (столицей) региона; 3 – объект является одновременно и центром района и центром региона';
COMMENT ON COLUMN OBJECT.OPERSTATUS IS 'Статус действия над записью – причина появления записи. Принимает значение: 01 – Инициация; 10 – Добавление; 20 – Изменение; 21 – Групповое изменение; 30 – Удаление; 31 – Удаление вследствие удаления вышестоящего объекта; 40 – Присоединение адресного объекта (слияние); 41 – Переподчинение вследствие слияния вышестоящего объекта; 42 – Прекращение существования вследствие присоединения к другому адресному объекту; 43 – Создание нового адресного объекта в результате слияния адресных объектов; 50 – Переподчинение; 51 – Переподчинение вследствие переподчинения вышестоящего объекта; 60 – Прекращение существования вследствие дробления; 61 – Создание нового адресного объекта в результате дробления; 70 – Восстановление прекратившего существование объекта';
COMMENT ON COLUMN OBJECT.CURRSTATUS IS 'Статус актуальности КЛАДР 4 (последние две цифры в коде)';
COMMENT ON COLUMN OBJECT.STARTDATE  IS 'Начало действия записи';
COMMENT ON COLUMN OBJECT.ENDDATE    IS 'Окончание действия записи';
COMMENT ON COLUMN OBJECT.NORMDOC    IS 'Внешний ключ на нормативный документ';
COMMENT ON COLUMN OBJECT.LIVESTATUS IS 'Признак действующего адресного объекта: 0 – недействующий адресный объект; 1 - действующий';

-- 3. Сведения об  элементах адреса, идентифицирующих адресуемые объекты

CREATE TABLE HOUSE (
	POSTALCODE  VARCHAR(6),
	IFNSFL      VARCHAR(4),
	TERRIFNSFL  VARCHAR(4),
	IFNSUL      VARCHAR(4),
	TERRIFNSUL  VARCHAR(4),
	OKATO       VARCHAR(11),
	OKTMO       VARCHAR(8),
	UPDATEDATE  DATE          NOT NULL,
	HOUSENUM    VARCHAR(20),
	ESTSTATUS   DECIMAL(1)    NOT NULL,
	BUILDNUM    VARCHAR(10),
	STRUCNUM    VARCHAR(10),
	STRSTATUS   DECIMAL(10)   NOT NULL,
	HOUSEID     VARCHAR(36)   NOT NULL,
	HOUSEGUID   VARCHAR(36)   NOT NULL,
	AOGUID      VARCHAR(36)   NOT NULL,
	STARTDATE   DATE          NOT NULL,
	ENDDATE     DATE          NOT NULL,
	STATSTATUS  DECIMAL(10)   NOT NULL,
	NORMDOC     VARCHAR(36),
	COUNTER     DECIMAL(10)   NOT NULL
);
COMMENT ON TABLE  HOUSE            IS 'Информация о номерах отдельных домов, владений, домовладений, корпусов, строений и земельных участках';
COMMENT ON COLUMN HOUSE.POSTALCODE IS 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект';
COMMENT ON COLUMN HOUSE.IFNSFL     IS 'Код ИФНС ФЛ. Содержит код инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSE.TERRIFNSFL IS 'Код территориального участка ИФНС ФЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSE.IFNSUL     IS 'Код ИФНС ЮЛ. Содержит код инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSE.TERRIFNSUL IS 'Код территориального участка ИФНС ЮЛ.Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSE.OKATO      IS 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня,  на территории которого находится дом, строение, земельный участок (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов)';
COMMENT ON COLUMN HOUSE.OKTMO      IS 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект';
COMMENT ON COLUMN HOUSE.UPDATEDATE IS 'Дата время внесения (обновления) записи';
COMMENT ON COLUMN HOUSE.HOUSENUM   IS 'Номер дома (земельного участка), корпуса, строения';
COMMENT ON COLUMN HOUSE.ESTSTATUS  IS 'Признак владения. Принимает следующие значения: 1 – владение; 2 – дом; 3 – домовладение; 4 – участок';
COMMENT ON COLUMN HOUSE.BUILDNUM   IS 'Номер корпуса';
COMMENT ON COLUMN HOUSE.STRUCNUM   IS 'Номер строения';
COMMENT ON COLUMN HOUSE.STRSTATUS  IS 'Признак строения. Принимает следующие значения: 1 – строение; 2 – сооружение; 3 – литер';
COMMENT ON COLUMN HOUSE.HOUSEID    IS 'Уникальный идентификатор записи дома. Используется для связи с нижестоящими адресными объектами';
COMMENT ON COLUMN HOUSE.HOUSEGUID  IS 'Глобальный уникальный идентификатор дома';
COMMENT ON COLUMN HOUSE.AOGUID     IS 'Guid записи родительского объекта (улицы, города, населенного пункта и т.п.). Содержит идентификационный код адресного объекта по классификатору адресообразующих элементов, на территории которого находится дом';
COMMENT ON COLUMN HOUSE.STARTDATE  IS 'Начало действия записи';
COMMENT ON COLUMN HOUSE.ENDDATE    IS 'Окончание действия записи';
COMMENT ON COLUMN HOUSE.STATSTATUS IS 'Состояние дома. Содержит коды состояния домов, используемые в БТИ';
COMMENT ON COLUMN HOUSE.NORMDOC    IS 'Внешний ключ на нормативный документ';
COMMENT ON COLUMN HOUSE.COUNTER    IS 'Счетчик записей домов для КЛАДР 4';

CREATE TABLE HOUSEINTERVAL (
	POSTALCODE  VARCHAR(6),
	IFNSFL      VARCHAR(4),
	TERRIFNSFL  VARCHAR(4),
	IFNSUL      VARCHAR(4),
	TERRIFNSUL  VARCHAR(4),
	OKATO       VARCHAR(11),
	OKTMO       VARCHAR(8),
	UPDATEDATE  DATE          NOT NULL,
	INTSTART    DECIMAL(10)   NOT NULL,
	INTEND      DECIMAL(10)   NOT NULL,
	HOUSEINTID  VARCHAR(36)   NOT NULL,
	INTGUID     VARCHAR(36)   NOT NULL,
	AOGUID      VARCHAR(36)   NOT NULL,
	STARTDATE   DATE          NOT NULL,
	ENDDATE     DATE          NOT NULL,
	INTSTATUS   DECIMAL(10)   NOT NULL,
	NORMDOC     VARCHAR(36),
	COUNTER     DECIMAL(10)   NOT NULL
);
COMMENT ON TABLE  HOUSEINTERVAL            IS 'Информация об интервалах номеров домов. При этом предполагается, что в указанных интервалах содержатся сведения обо всех номерах домов, владений, домовладений, корпусов и строений и их сочетаний';
COMMENT ON COLUMN HOUSEINTERVAL.POSTALCODE IS 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект';
COMMENT ON COLUMN HOUSEINTERVAL.IFNSFL     IS 'Код ИФНС ФЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSEINTERVAL.TERRIFNSFL IS 'Код территориального участка ИФНС ФЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSEINTERVAL.IFNSUL     IS 'Код ИФНС ЮЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSEINTERVAL.TERRIFNSUL IS 'Код территориального участка ИФНС ЮЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN HOUSEINTERVAL.OKATO      IS 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня, на территории которого находится дома с указанным интервалом (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов)';
COMMENT ON COLUMN HOUSEINTERVAL.OKTMO      IS 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект';
COMMENT ON COLUMN HOUSEINTERVAL.UPDATEDATE IS 'Дата  внесения (обновления) записи';
COMMENT ON COLUMN HOUSEINTERVAL.INTSTART   IS 'Значение начала интервала. Определяет начало интервала номеров домов. Для четных интервалов обозначается четной цифрой, для нечетных интервалов – нечетной';
COMMENT ON COLUMN HOUSEINTERVAL.INTEND     IS 'Значение окончания интервала. Определяет конец интервала номеров домов. Для четных интервалов обозначается четной цифрой, для нечетных интервалов – нечетной, для обычных интервалов – любой цифрой';
COMMENT ON COLUMN HOUSEINTERVAL.HOUSEINTID IS 'Идентификатор записи интервала домов';
COMMENT ON COLUMN HOUSEINTERVAL.INTGUID    IS 'Глобальный уникальный идентификатор интервала домов';
COMMENT ON COLUMN HOUSEINTERVAL.AOGUID     IS 'Идентификатор объекта родительского объекта (улицы, города, населенного пункта и т.п.). Содержит идентификационный код объекта адресного объекта по классификатору адресообразующих элементов, на территории которого дома из данного интервала';
COMMENT ON COLUMN HOUSEINTERVAL.STARTDATE  IS 'Начало действия записи';
COMMENT ON COLUMN HOUSEINTERVAL.ENDDATE    IS 'Окончание действия записи';
COMMENT ON COLUMN HOUSEINTERVAL.INTSTATUS  IS 'Статус интервала (обычный, четный, нечетный). Может принимать следующие значения: 1 – для обычных интервалов, включающих четные и нечетные номера домов; 2 – для четных интервалов, включающих четные номера домов; 3 – для нечетных интервалов, включающих нечетные номера домов';
COMMENT ON COLUMN HOUSEINTERVAL.NORMDOC    IS 'Внешний ключ на нормативный документ';
COMMENT ON COLUMN HOUSEINTERVAL.COUNTER    IS 'Счетчик записей домов для КЛАДР 4';

CREATE TABLE LANDMARK (
	LOCATION    VARCHAR(500)  NOT NULL,
	POSTALCODE  VARCHAR(6),
	IFNSFL      VARCHAR(4),
	TERRIFNSFL  VARCHAR(4),
	IFNSUL      VARCHAR(4),
	TERRIFNSUL  VARCHAR(4),
	OKATO       VARCHAR(11),
	OKTMO       VARCHAR(8),
	UPDATEDATE  DATE          NOT NULL,
	LANDID      VARCHAR(36)   NOT NULL,
	LANDGUID    VARCHAR(36)   NOT NULL,
	AOGUID      VARCHAR(36)   NOT NULL,
	STARTDATE   DATE          NOT NULL,
	ENDDATE     DATE          NOT NULL,
	NORMDOC     VARCHAR(36)
);
COMMENT ON TABLE  LANDMARK            IS 'Дополнительное неформализованное текстовое описание места расположения объекта адресации относительно ориентиров на местности';
COMMENT ON COLUMN LANDMARK.LOCATION   IS 'Месторасположение ориентира. содержит описание места нахождения объекта недвижимости (“ориентир”), для которого нельзя указать точный структурированный адрес (почтовый адрес). Значение блока записывается в произвольном виде, позволяющем однозначно определить местонахождение объекта недвижимости через известные ориентиры на местности';
COMMENT ON COLUMN LANDMARK.POSTALCODE IS 'Почтовый индекс. Содержит почтовый индекс предприятия почтовой связи, обслуживающего данный адресный объект';
COMMENT ON COLUMN LANDMARK.IFNSFL     IS 'Код ИФНС ФЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN LANDMARK.TERRIFNSFL IS 'Код территориального участка ИФНС ФЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих физических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN LANDMARK.IFNSUL     IS 'Код ИФНС ЮЛ. Содержит коды инспекций ФНС России по ведомственному ФНС России классификатору "Система обозначений налоговых органов" (СОНО), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN LANDMARK.TERRIFNSUL IS 'Код территориального участка ИФНС ЮЛ. Содержит коды территориальных участков* (упраздненных инспекций, преобразованных в подразделения межрайонных инспекций: отделы, территориальные участки и т.п.) ИФНС России по ведомственному справочнику кодов обозначений налоговых органов для целей учета налогоплательщиков (СОУН), обслуживающих юридических лиц на территории, на которой расположен данный адресный объект';
COMMENT ON COLUMN LANDMARK.OKATO      IS 'OKATO. Содержит код объекта административно-территориального деления по общероссийскому классификатору ОКАТО соответствующего уровня, на территории которого находится адресуемый объект (от субъекта РФ до сельского населенного пункта и внутригородских районов или внутригородских округов)';
COMMENT ON COLUMN LANDMARK.OKTMO      IS 'OKTMO. Содержит код муниципального образования по Общероссийскому классификатору территорий муниципальных образований, на территории которого расположен адресуемый объект';
COMMENT ON COLUMN LANDMARK.UPDATEDATE IS 'Дата  внесения (обновления) записи';
COMMENT ON COLUMN LANDMARK.LANDID     IS 'Уникальный идентификатор записи ориентира';
COMMENT ON COLUMN LANDMARK.LANDGUID   IS 'Глобальный уникальный идентификатор ориентира';
COMMENT ON COLUMN LANDMARK.AOGUID     IS 'Уникальный идентификатор родительского объекта (улицы, города, населенного пункта и т.п.). Содержит идентификационный код адресного объекта по классификатору адресообразующих элементов, на территории которого находится адресуемый объект';
COMMENT ON COLUMN LANDMARK.STARTDATE  IS 'Начало действия записи';
COMMENT ON COLUMN LANDMARK.ENDDATE    IS 'Окончание действия записи';
COMMENT ON COLUMN LANDMARK.NORMDOC    IS 'Внешний ключ на нормативный документ';

-- Состав и структура файла с информацией по нормативным документам, являющимся основанием присвоения адресному элементу наименования в базе данных Федеральной информационной справочной системы

CREATE TABLE NORMATIVEDOCUMENT (
	NORMDOCID   VARCHAR(36)   NOT NULL,
	DOCNAME     TEXT,
	DOCDATE     DATE,
	DOCNUM      VARCHAR(20),
	DOCTYPE     DECIMAL(10)   NOT NULL,
	DOCIMGID    DECIMAL(10)
);
COMMENT ON TABLE  NORMATIVEDOCUMENT           IS 'Сведения по нормативному документу, являющемуся основанием присвоения адресному элементу наименования';
COMMENT ON COLUMN NORMATIVEDOCUMENT.NORMDOCID IS 'Идентификатор нормативного документа';
COMMENT ON COLUMN NORMATIVEDOCUMENT.DOCNAME   IS 'Наименование документа';
COMMENT ON COLUMN NORMATIVEDOCUMENT.DOCDATE   IS 'Дата документа';
COMMENT ON COLUMN NORMATIVEDOCUMENT.DOCNUM    IS 'Номер документа';
COMMENT ON COLUMN NORMATIVEDOCUMENT.DOCTYPE   IS 'Тип документа';
COMMENT ON COLUMN NORMATIVEDOCUMENT.DOCIMGID  IS 'Идентификатор образа (внешний ключ)';

CREATE TABLE NORMATIVEDOCUMENTTYPE (
	NDTYPEID    DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(100)  NOT NULL
);
COMMENT ON TABLE  NORMATIVEDOCUMENTTYPE          IS 'Сведения по типу нормативного документа, являющегося основанием присвоения адресному элементу наименования';
COMMENT ON COLUMN NORMATIVEDOCUMENTTYPE.NDTYPEID IS 'Идентификатор типа нормативного документа';
COMMENT ON COLUMN NORMATIVEDOCUMENTTYPE.NAME     IS 'Наименование';

-- Справочные сведения

CREATE TABLE ADDRESSOBJECTTYPE (
	LEVEL       DECIMAL(10)   NOT NULL,
	SCNAME      VARCHAR(10),
	SOCRNAME    VARCHAR(50)   NOT NULL,
	KOD_T_ST    VARCHAR(4)    NOT NULL
);
COMMENT ON TABLE  ADDRESSOBJECTTYPE          IS 'Тип адресного объекта';
COMMENT ON COLUMN ADDRESSOBJECTTYPE.LEVEL    IS 'Уровень адресного объекта';
COMMENT ON COLUMN ADDRESSOBJECTTYPE.SCNAME   IS 'Краткое наименование типа объекта';
COMMENT ON COLUMN ADDRESSOBJECTTYPE.SOCRNAME IS 'Полное наименование типа объекта';
COMMENT ON COLUMN ADDRESSOBJECTTYPE.KOD_T_ST IS 'Ключевое поле';

CREATE TABLE CURRENTSTATUS (
	CURENTSTID  DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(100)  NOT NULL
);
COMMENT ON TABLE  CURRENTSTATUS            IS 'Статус актуальности КЛАДР 4.0';
COMMENT ON COLUMN CURRENTSTATUS.CURENTSTID IS 'Идентификатор статуса (ключ). Принимает значения: 0 – актуальный; 1-50, 2-98 – исторический; 51 – переподчиненный; 99 - несуществующий';
COMMENT ON COLUMN CURRENTSTATUS.NAME       IS 'Наименование';

CREATE TABLE ACTUALSTATUS (
	ACTSTATID   DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(100)  NOT NULL
);
COMMENT ON TABLE  ACTUALSTATUS           IS 'Статус актуальности ФИАС';
COMMENT ON COLUMN ACTUALSTATUS.ACTSTATID IS 'Идентификатор статуса (ключ). Принимает значения: 0 – Не актуальный; 1 – Актуальный (последняя запись по адресному объекту)';
COMMENT ON COLUMN ACTUALSTATUS.NAME      IS 'Наименование';

CREATE TABLE OPERATIONSTATUS (
	OPERSTATID  DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(100)  NOT NULL
);
COMMENT ON TABLE  OPERATIONSTATUS            IS 'Статус действия';
COMMENT ON COLUMN OPERATIONSTATUS.OPERSTATID IS 'Идентификатор статуса (ключ). Принимает значения: 01 – Инициация; 10 – Добавление; 20 – Изменение; 21 – Групповое изменение; 30 – Удаление; 31 - Удаление вследствие удаления вышестоящего объекта; 40 – Присоединение адресного объекта (слияние); 41 – Переподчинение вследствие слияния вышестоящего объекта; 42 - Прекращение существования вследствие присоединения к другому адресному объекту; 43 - Создание нового адресного объекта в результате слияния адресных объектов; 50 – Переподчинение; 51 – Переподчинение вследствие переподчинения вышестоящего объекта; 60 – Прекращение существования вследствие дробления; 61 – Создание нового адресного объекта в результате дробления; 70 – Восстановление прекратившего существование объекта';
COMMENT ON COLUMN OPERATIONSTATUS.NAME       IS 'Наименование';

CREATE TABLE CENTERSTATUS (
	CENTERSTID  DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(100)  NOT NULL
);
COMMENT ON TABLE  CENTERSTATUS            IS 'Статус центра';
COMMENT ON COLUMN CENTERSTATUS.CENTERSTID IS 'Идентификатор статуса';
COMMENT ON COLUMN CENTERSTATUS.NAME       IS 'Наименование';

CREATE TABLE INTERVALSTATUS (
	INTVSTATID  DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(60)   NOT NULL
);
COMMENT ON TABLE  INTERVALSTATUS            IS 'Статус интервала домов';
COMMENT ON COLUMN INTERVALSTATUS.INTVSTATID IS 'Идентификатор статуса (обычный, четный, нечетный). Принимает значения: 0 – Не определено; 1 – Обычный; 2 – Четный; 3 – Нечетный';
COMMENT ON COLUMN INTERVALSTATUS.NAME       IS 'Наименование';

CREATE TABLE HOUSESTATESTATUS (
	HOUSESTID   DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(60)   NOT NULL
);
COMMENT ON TABLE  HOUSESTATESTATUS           IS 'Статус состояния домов';
COMMENT ON COLUMN HOUSESTATESTATUS.HOUSESTID IS 'Идентификатор статуса';
COMMENT ON COLUMN HOUSESTATESTATUS.NAME      IS 'Наименование';

CREATE TABLE ESTATESTATUS (
	ESTSTATID   DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(20)   NOT NULL,
	SHORTNAME   VARCHAR(20)
);
COMMENT ON TABLE  ESTATESTATUS           IS 'Признак владения';
COMMENT ON COLUMN ESTATESTATUS.ESTSTATID IS 'Признак владения. Принимает значение: 0 – Не определено; 1 – Владение; 2 – Дом; 3 – Домовладение';
COMMENT ON COLUMN ESTATESTATUS.NAME      IS 'Наименование';
COMMENT ON COLUMN ESTATESTATUS.SHORTNAME IS 'Краткое наименование';

CREATE TABLE STRUCTURESTATUS (
	STRSTATID   DECIMAL(10)   NOT NULL,
	NAME        VARCHAR(20)   NOT NULL,
	SHORTNAME   VARCHAR(20)
);
COMMENT ON TABLE  STRUCTURESTATUS           IS 'Признак строения';
COMMENT ON COLUMN STRUCTURESTATUS.STRSTATID IS 'Признак строения. Принимает значение: 0 – Не определено; 1 – Строение; 2 – Сооружение; 3 – Литер';
COMMENT ON COLUMN STRUCTURESTATUS.NAME      IS 'Наименование';
COMMENT ON COLUMN STRUCTURESTATUS.SHORTNAME IS 'Краткое наименование';