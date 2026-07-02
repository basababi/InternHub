package mn.internhub.demo.api.dto.OrganizationReviewApi;


public record RequestPostReview(
        Float rating,
        String comment,
        Boolean isanonymous

) {
}
