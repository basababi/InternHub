package mn.internhub.demo.api.dto.OrganizationReviewApi;

public record RequestUpdateReview(
         Float rating,
         String comment,
         boolean isAnonymous
) {
}
