SELECT XmlElement("Movies",
  XmlAttributes(mov.idMovie as "idMovie"),
  XmlForest(mov.name as "name"),
  --Country
  (
    SELECT XmlElement("Countries",(XmlAgg(XmlForest(par.nameCountry as "nameCountry"))))
    FROM PARTICIPER_CB par
    WHERE par.idMovie = mov.idMovie
  ),
  --Acteurs
  (
    SELECT XmlElement("Acteurs",(XmlAgg(XmlForest(cas.nameCast as "nameCast"))))
    FROM CASTS_CB cas inner join JOUER_CB jou
    on(cas.idCast = jou.idCast)
    WHERE jou.idMovie = mov.idMovie
    and jou.nameJob = 'actor'
  ),
  --Genres
  (
    SELECT XmlElement("Genres",(XmlAgg(XmlForest(app.nameGenre as "nameGenre"))))
    FROM APPARTENIR_CB app
    WHERE app.idMovie = mov.idMovie
  ),
  --Copies CB
  (
  SELECT XmlAgg(
    XmlElement("Copies",
        XmlAttributes('CB' as "Complexe", copCB.idCopie as "idCopie")
    )
  )
  FROM COPIES_CB copCB
  WHERE copCB.idMovie = mov.idMovie
  ),
  --Copies CC1
  (
  SELECT XmlAgg(
    XmlElement("Copies",
        XmlAttributes('CC1' as "Complexe", copCC1.idCopie as "idCopie", copCC1.deleted as "deleted"),
      --Projection
      (
      SELECT XmlAgg(
        XmlElement("Projections",
          XmlForest(proCC1.dateHeureProjection as "dateHeureProjection", proCC1.numeroSalle as "numeroSalle",
                --Nombre de places achetée
                (
                  SELECT sum(comCC1.nbre)
                  FROM COMMANDERTICKET_CC1 comCC1
                  WHERE comCC1.dateHeureProjection = proCC1.dateHeureProjection
                  AND comCC1.numeroSalle = proCC1.numeroSalle
                )
                as "TicketsAchetés",
                --Capacité
                (
                  SELECT salCC1.capacite
                  FROM SALLE_CC1 salCC1
                  WHERE salCC1.numeroSalle = proCC1.numeroSalle
                )
                as "Capacité"
            )
        )
      )
      FROM PROJECTION_CC1 proCC1
      WHERE proCC1.idCopie = copCC1.idCopie
      )
    )
  )
  FROM COPIES_CC1 copCC1
  WHERE copCC1.idMovie = mov.idMovie
  ),
  --Copies CC2
  (
  SELECT XmlAgg(
    XmlElement("Copies",
        XmlAttributes('CC2' as "Complexe", copCC2.idCopie as "idCopie", copCC2.deleted as "deleted"),
      --Projection
      (
      SELECT XmlAgg(
        XmlElement("Projections",
          XmlForest(proCC2.dateHeureProjection as "dateHeureProjection", proCC2.numeroSalle as "numeroSalle",
                --Nombre de places achetée
                (
                  SELECT sum(comCC2.nbre)
                  FROM PASSERCOMMANDE_CC2 comCC2
                  WHERE comCC2.dateHeureProjection = proCC2.dateHeureProjection
                  AND comCC2.numeroSalle = proCC2.numeroSalle
                )
                as "TicketsAchetés",
                --Capacité
                (
                  SELECT salCC2.capacite
                  FROM SALLE_CC2 salCC2
                  WHERE salCC2.numeroSalle = proCC2.numeroSalle
                )
                as "Capacité"
            )
        )
      )
      FROM PROJECTION_CC2 proCC2
      WHERE proCC2.idCopie = copCC2.idCopie
      )
    )
  )
  FROM COPIES_CC2 copCC2
  WHERE copCC2.idMovie = mov.idMovie
  ),
  --Commandes CC1
  (
  SELECT XmlAgg(
    XmlElement("Commandes",
        XmlAttributes('CC1' as "Complexe", ComCC1.deleted as "deleted"),
        XmlForest(ComCC1.dateHeure as "dateHeure", ComCC1.nbCopie as "nbCopie")
    )
  )
  FROM COMMANDER_CB ComCC1
  WHERE comCC1.idMovie = mov.idMovie
  AND idComplexe = (select idComplexe from COMPLEXE_CB where nameComplexe = 'CC1')
  ),
  --Commandes CC2
  (
  SELECT XmlAgg(
    XmlElement("Commandes",
        XmlAttributes('CC2' as "Complexe", ComCC2.deleted as "deleted"),
        XmlForest(ComCC2.dateHeure as "dateHeure", ComCC2.nbCopie as "nbCopie")
    )
  )
  FROM COMMANDER_CB ComCC2
  WHERE comCC2.idMovie = mov.idMovie
  AND idComplexe = (select idComplexe from COMPLEXE_CB where nameComplexe = 'CC2')
  )
) as movies
from MOVIES_CB mov
where mov.idMovie = (select idMovie from COPIES_CC1 where idCopie = 1)