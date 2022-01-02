package com.eureka.ationserver.domain.insight;

import com.eureka.ationserver.domain.persona.Interest;
import com.eureka.ationserver.domain.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Insight.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insight_id")
    private Insight insight;

    @ManyToOne(targetEntity = InsightSubCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insightsubcategory_id")
    private InsightSubCategory insightSubCategory;
}