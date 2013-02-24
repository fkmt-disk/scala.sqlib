create table m_address(
    row_id integer not null
  , zip_code char(7) not null
  , state varchar(128) not null
  , city varchar(128) not null
  , town varchar(128) not null
  , modify_at timestamp not null default current timestamp
  , constraint m_address_pkc primary key (row_id)
);
