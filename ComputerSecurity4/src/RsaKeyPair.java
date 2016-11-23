public class RsaKeyPair {

	private final PrivateKey privateKey;

	private final PublicKey publicKey;

	public RsaKeyPair(PublicKey publicKey, PrivateKey privateKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}