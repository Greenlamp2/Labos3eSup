SELECT XmlElement("Movies",
  XmlAttributes(mov.idMovie as "idMovie"),
  XmlForest(mov.name as "name"),
  XmlAgg(
    XmlElement("Countries",
      XmlForest( par.nameCountry as "nameCountry")
    )
  ),
  XmlAgg(
    XmlElement("Cast",
      XmlForest( cas.nameCast as "nameCast")
    )
  )
) as movies
from Movies mov inner join Participer par
on mov.idMovie = par.idMovie
inner join Jouer jou on mov.idMovie = jou.idMovie
inner join Casts cas on cas.idCast = jou.idCast
group by(mov.idMovie, mov.name, par.nameCountry)